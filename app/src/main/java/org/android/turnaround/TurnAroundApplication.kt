package org.android.turnaround

import android.app.Application
import org.android.turnaround.util.TurnAroundDebugTree
import timber.log.Timber

class TurnAroundApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(TurnAroundDebugTree())
    }
}
