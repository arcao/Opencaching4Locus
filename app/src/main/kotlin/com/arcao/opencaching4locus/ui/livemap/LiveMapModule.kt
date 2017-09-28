package com.arcao.opencaching4locus.ui.livemap

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
abstract class LiveMapModule {
    @Provides
    internal fun providesNotificationManager(context: Context) : NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    internal fun providesAlarmManager(context: Context) : AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}