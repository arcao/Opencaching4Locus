package com.arcao.opencaching4locus

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @JvmStatic
    @Provides
    fun providesContext(app: App): Context = app

    @JvmStatic
    @Provides
    fun provideSharedPreferences(context: Context) : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
}