package cl.konadev.bluekapp

import android.app.Application
import timber.log.Timber

@Suppress("Unused")
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}