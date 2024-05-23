package com.example.yandexmaps

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class App : Application() {

    companion object {
        private const val MAPKIT_API_KEY = "09e1d88a-c704-46e4-965c-32284c69d62f"
    }

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }
}