package com.example.itemizer

import android.app.Application
import com.example.itemizer.data.AppContainer
import com.example.itemizer.data.DefaultAppContainer

class ItemizerApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}