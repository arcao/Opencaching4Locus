package com.arcao.opencaching4locus

import android.util.Log
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    internal fun logInjection() {
        Log.i(TAG, "Injecting " + TAG)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerApp_Component.builder().create(this)
    }


    // =============== Dagger 2 ===============
    @dagger.Component(modules = arrayOf(AndroidSupportInjectionModule::class))
    internal interface Component : AndroidInjector<App> {
        @dagger.Component.Builder
        abstract class Builder : AndroidInjector.Builder<App>()
    }

    companion object {
        private val TAG = App::class.java.simpleName
    }
}
