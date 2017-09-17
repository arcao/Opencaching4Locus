package com.arcao.opencaching4locus

import android.content.Context
import dagger.Binds

@dagger.Module
abstract class AppModule {
    @Binds
    abstract fun context(app: App): Context
}