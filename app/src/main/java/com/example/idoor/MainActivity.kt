package com.example.idoor

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.example.idoor.navigation.NavGraph
import com.example.idoor.navigation.Screen
import com.example.idoor.nfc.NfcHelper
import com.example.idoor.screen.OnBoardingScreen
import com.example.idoor.screen.OnBoardingViewModel
import com.example.idoor.ui.theme.IDoorTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.compose.runtime.CompositionLocalProvider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var nfcHelper: NfcHelper

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = NfcAdapter.getDefaultAdapter(this)
        adapter?.enableReaderMode(this, nfcHelper, NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B, null)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading
            }
        }

        val isFirstTime = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            .getBoolean("isFirstTime", true)

        if (isFirstTime) {
            val androidId = Settings.Secure.getString(this@MainActivity.contentResolver, Settings.Secure.ANDROID_ID)
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL

            val deviceModel = "$manufacturer $model"

            viewModel.saveDeviceInfo(deviceId = androidId, deviceModel = deviceModel)

            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("isFirstTime", false)
                .apply()

        }

        setContent {
            IDoorTheme {
                val systemController = rememberSystemUiController()
                val navController = rememberNavController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent
                    )
                }


                var startDestination = viewModel.startDestination

                LaunchedEffect(viewModel.startDestination) {
                    startDestination = viewModel.startDestination
                }

                if (startDestination.isNotEmpty()) {
                    if (startDestination == Screen.DashboardUser.route || startDestination == Screen.DashboardAdmin.route){
                        activateNfc()
                    } else {
                        deactivateNfc()
                    }
                    NavGraph(navController = navController, startDestination = startDestination)
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Disable NFC reader mode when the activity is destroyed
        val adapter = NfcAdapter.getDefaultAdapter(this)
        adapter?.disableReaderMode(this)
    }

    fun activateNfc() {
        val adapter = NfcAdapter.getDefaultAdapter(this)
        adapter?.enableReaderMode(
            this,
            nfcHelper,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_NFC_B,
            null
        )
    }

    fun deactivateNfc() {
        val adapter = NfcAdapter.getDefaultAdapter(this)
        adapter?.disableReaderMode(this)
    }
}

@Preview(showBackground = true, apiLevel = Build.VERSION_CODES.O)
@Composable
fun IDoorPreview() {
    IDoorTheme {
        val navController = rememberNavController()
        val viewModel: OnBoardingViewModel = hiltViewModel()
        OnBoardingScreen(navController = navController, viewModel = viewModel)
    }
}