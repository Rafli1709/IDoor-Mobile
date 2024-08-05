package com.example.idoor

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()