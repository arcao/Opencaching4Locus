package com.arcao.opencaching4locus

import android.util.Log
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {
    companion object {
        private val TAG = App::class.java.simpleName
    }

    @Inject
    fun logInjection() {
        Log.i(TAG, "Injecting " + TAG)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().create(this)
}
