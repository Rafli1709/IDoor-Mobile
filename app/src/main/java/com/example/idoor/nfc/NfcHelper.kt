package com.example.idoor.nfc

import android.content.Context
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.idoor.data.models.accesshistory.CreateAccessHistory
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.repository.AccessHistoryRepository
import com.example.idoor.util.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NfcHelper @Inject constructor(
    private val context: Context,
    private val accessHistoryRepository: AccessHistoryRepository,
    private val dataStoreRepository: DataStoreRepository,
) : NfcAdapter.ReaderCallback {

    private val payloadList: MutableList<String> = mutableListOf()
    private var statusAccess by mutableStateOf("")
    private val handler = Handler(Looper.getMainLooper())

    override fun onTagDiscovered(tag: Tag?) {
        val startTime = System.currentTimeMillis()

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))

        val uid = tag?.id?.joinToString(":") { byte -> String.format("%02X", byte) }

        Log.d("NFC Reader", "NFC Tag Discovered")
        Log.d("NFC Reader", "Tag UID: $uid")

        readNdefMessage(tag)

        val endTime = System.currentTimeMillis()
        val elapsedTime = (endTime - startTime) / 1000.0

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "Waktu Akses: $elapsedTime milliseconds", Toast.LENGTH_SHORT).show()
        }

        handler.postDelayed({}, 1000)
    }

    private fun readNdefMessage(tag: Tag?) {
        val ndef = Ndef.get(tag)
        if (ndef != null) {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage
            if (ndefMessage != null) {
                processNdefMessage(ndefMessage)
                ProcessLifecycleOwner.get().lifecycleScope.launch {
                    Log.d("NFC Reader", "Masuk")
                    val userIdFlow = dataStoreRepository.readUserId()
                    val userId = userIdFlow.first() ?: 0
                    val deviceIdFlow = dataStoreRepository.readDeviceId()
                    val deviceId = deviceIdFlow.first()
                    val deviceModelFlow = dataStoreRepository.readDeviceModel()
                    val deviceModel = deviceModelFlow.first()
                    Log.d("NFC Reader", payloadList[2])
                    when (
                        val result = accessHistoryRepository.createAccessHistory(
                            CreateAccessHistory(
                                userId = userId,
                                imeiTool = payloadList[0],
                                password = payloadList[1],
                                doorStatus = payloadList[2],
                                imeiAccess = deviceId,
                                deviceModel = deviceModel
                            )
                        )
                    ) {
                        is Resource.Success -> {
                            Log.d("NFC Reader", result.data!!.message)
                            statusAccess = result.data.message
                        }

                        is Resource.Error -> {
                            Log.d("NFC Reader", "Cannot send data to server")
                        }
                    }
                }
                if (statusAccess.isNotEmpty()) {
                    if (ndef.isWritable) {
                        Log.d("NFC Reader", "Status: $statusAccess")
                        ndef.writeNdefMessage(createNdefTextMessage(statusAccess))
                    } else {
                        Log.d("NFC Reader", "Cannot write the tag")
                    }
                }
            }
            ndef.close()
        } else {
            Log.d("NFC Reader", "Tag does not support NDEF")
        }
    }

    private fun createNdefTextMessage(text: String): NdefMessage {
        val textBytes = text.toByteArray(Charset.forName("UTF-8"))
        val textRecord = NdefRecord(
            NdefRecord.TNF_WELL_KNOWN,
            NdefRecord.RTD_TEXT,
            byteArrayOf(),
            textBytes
        )
        return NdefMessage(textRecord)
    }

    fun writeNdefMessage(tag: Tag, message: NdefMessage): Boolean {
        val ndef = Ndef.get(tag)
        return if (ndef != null && ndef.isWritable) {
            ndef.connect()
            ndef.writeNdefMessage(message)
            ndef.close()
            true
        } else {
            false
        }
    }

    private fun processNdefMessage(ndefMessage: NdefMessage) {
        payloadList.clear()
        for (record in ndefMessage.records) {
            val payload = record.payload
            val cleanedPayload = cleanPayload(payload)
            Log.d("NFC Reader", "Payload = $cleanedPayload")
            payloadList.add(cleanedPayload)
        }
    }

    private fun cleanPayload(payload: ByteArray): String {
        val text = String(payload, Charsets.UTF_8)
        val cleanedText = text.trim { it <= ' ' }
        return cleanedText.removePrefix("en")
    }

}
