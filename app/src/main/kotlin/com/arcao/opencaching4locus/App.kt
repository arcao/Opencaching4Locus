package com.arcao.opencaching4locus

import android.content.Context
import android.util.Log
import com.arcao.opencaching4locus.data.DataModule
import com.arcao.opencaching4locus.data.network.OkApiService
import com.arcao.opencaching4locus.data.network.OkApiServiceType
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {

    @Inject
    lateinit var services : Map<OkApiServiceType, @JvmSuppressWildcards OkApiService>

    @Inject
    internal fun logInjection() {
        Log.i(TAG, "Injecting " + TAG)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        println(services.toString())
    }

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerApp_Component.builder().create(this)
    }


    // =============== Dagger 2 ===============
    @dagger.Component(modules = arrayOf(AndroidSupportInjectionModule::class, DataModule::class, Module::class))
    internal interface Component : AndroidInjector<App> {
        fun context() : Context

        @dagger.Component.Builder
        abstract class Builder : AndroidInjector.Builder<App>() {
            abstract override fun build(): Component
        }
    }

    @dagger.Module
    internal abstract inner class Module(private val app: App) {
        @Provides fun provideContext() : Context {
            return app
        }
    }

    companion object {
        private val TAG = App::class.java.simpleName
    }
}
