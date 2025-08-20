package com.chnkcksk.recipeshiltapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecipeHiltApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}