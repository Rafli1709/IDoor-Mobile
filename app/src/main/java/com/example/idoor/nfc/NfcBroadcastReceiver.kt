package com.example.idoor.nfc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.util.Log

class NfcBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action || NfcAdapter.ACTION_TAG_DISCOVERED == action) {
            val tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG) as? android.nfc.Tag
            Log.d("NFC Reader", "NFC Tag Discovered in the background")
            Log.d("NFC Reader", "Tag ID: ${tag?.id}")
        }
    }
}