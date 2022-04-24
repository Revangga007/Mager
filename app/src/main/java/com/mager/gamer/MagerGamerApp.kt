package com.mager.gamer

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.mager.gamer.data.local.MagerSharedPref
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MagerGamerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupHawk()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupHawk() {
        MagerSharedPref.appInit(this)
    }

}