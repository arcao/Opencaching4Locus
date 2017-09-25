package com.arcao.opencaching4locus

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule {
    @Provides
    fun providesContext(app: App): Context = app

    @Provides
    fun provideSharedPreferences(context: Context) : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
}