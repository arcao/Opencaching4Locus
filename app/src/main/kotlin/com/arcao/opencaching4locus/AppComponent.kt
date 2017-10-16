package com.arcao.opencaching4locus

import com.arcao.opencaching4locus.data.DataModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApp
@Component(modules = arrayOf(AndroidSupportInjectionModule::class, DataModule::class, AppModule::class, AndroidBindingModule::class))
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}