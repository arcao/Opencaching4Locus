package com.arcao.opencaching4locus

import com.arcao.opencaching4locus.data.DataModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@dagger.Component(modules = arrayOf(AndroidSupportInjectionModule::class, DataModule::class, AppModule::class, AndroidBindingModule::class))
interface AppComponent : AndroidInjector<App> {
    @dagger.Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}