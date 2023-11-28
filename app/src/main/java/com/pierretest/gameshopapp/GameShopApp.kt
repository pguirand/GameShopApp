package com.pierretest.gameshopapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GameShopApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}