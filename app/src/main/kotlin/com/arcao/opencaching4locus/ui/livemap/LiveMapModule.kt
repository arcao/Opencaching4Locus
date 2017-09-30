package com.arcao.opencaching4locus.ui.livemap

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
object LiveMapModule {
    @JvmStatic
    @Provides
    fun providesNotificationManager(context: Context) : NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @JvmStatic
    @Provides
    fun providesAlarmManager(context: Context) : AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}