package com.arcao.opencaching4locus.ui.livemap.manager

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.data.locusmap.util.checkLocusMap
import com.arcao.opencaching4locus.data.locusmap.util.isPeriodicUpdatesEnabled
import com.arcao.opencaching4locus.data.okapi.OkApiException
import com.arcao.opencaching4locus.ui.base.constants.AppConstants
import com.arcao.opencaching4locus.ui.base.constants.PrefConstants
import com.arcao.opencaching4locus.ui.error.util.showError
import com.arcao.opencaching4locus.ui.livemap.LiveMapJobService
import com.arcao.opencaching4locus.ui.livemap.receiver.LiveMapBroadcastReceiver
import com.arcao.opencaching4locus.ui.settings.SettingsActivity
import com.arcao.opencaching4locus.ui.settings.fragment.LiveMapPreferenceFragment
import timber.log.Timber
import javax.inject.Inject


class LiveMapNotificationManager @Inject constructor(
        private val context: Context,
        private val sharedPreferences: SharedPreferences,
        private val notificationManager: NotificationManager,
        private val alarmManager: AlarmManager
) {
    companion object {
        private const val VAR_B_MAP_VISIBLE = "1300"

        private const val ACTION_HIDE_NOTIFICATION = "com.arcao.opencaching4locus.action.HIDE_NOTIFICATION"
        private const val ACTION_LIVE_MAP_ENABLE = "com.arcao.opencaching4locus.action.LIVE_MAP_ENABLE"
        private const val ACTION_LIVE_MAP_DISABLE = "com.arcao.opencaching4locus.action.LIVE_MAP_DISABLE"
        private const val NOTIFICATION_TIMEOUT_MS = 2000

        private const val LIVE_MAP_CHANNEL = "com.arcao.opencaching4locus.LIVE_MAP_CHANNEL"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    var enable: Boolean
        get() = sharedPreferences.getBoolean(PrefConstants.LIVE_MAP, PrefConstants.LIVE_MAP_DEFAULT)
        set(value) {
            var newState = value
            sharedPreferences.edit().putBoolean(PrefConstants.LIVE_MAP, newState).apply()

            val locusInstalled = context.checkLocusMap()
            if (!locusInstalled)
                newState = false

            // hide visible geocaches when live map is disabling
            if (!newState && enable && locusInstalled) {
                LiveMapJobService.cleanMap(context)
            }

            if (newState && !context.isPeriodicUpdatesEnabled()) {
                newState = false

                context.showError {
                    message(R.string.error_live_map_periodic_updates)
                }
            } else if (newState) {
                Toast.makeText(context, context.getText(R.string.toast_live_map_enabled), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, context.getText(R.string.toast_live_map_disabled), Toast.LENGTH_LONG).show()
            }

            sharedPreferences.edit().putBoolean(PrefConstants.LIVE_MAP, newState).apply()
        }

    private var previousEnable = false
    private var notificationShown = false
        set(value) {
            if (!value)
                forceUpdateRequired = true
            field = value
        }

    var forceUpdateRequired: Boolean = true
        get() {
            val tmp = field; field = false; return tmp
        }
        private set

    fun handleBroadcastIntent(intent: Intent): Boolean {
        Timber.i(intent.action)

        when (intent.action) {
            ACTION_HIDE_NOTIFICATION -> {
                hideNotification()
                return true
            }
            ACTION_LIVE_MAP_ENABLE -> {
                enable = true
                showNotification()
                return true
            }
            ACTION_LIVE_MAP_DISABLE -> {
                enable = false
                showNotification()
                return true
            }
            else -> {
                if (!isMapVisible(intent)) {
                    return false
                }

                if (!notificationShown || previousEnable != enable) {
                    showNotification()
                    previousEnable = enable
                }
                updateNotificationHideAlarm()
                return false
            }
        }

    }

    private fun isMapVisible(intent: Intent): Boolean = intent.getBooleanExtra(VAR_B_MAP_VISIBLE, false)

    private fun updateNotificationHideAlarm() {
        val pendingIntent = createPendingIntent(ACTION_HIDE_NOTIFICATION)

        alarmManager.cancel(pendingIntent)
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + NOTIFICATION_TIMEOUT_MS, pendingIntent)
    }

    private fun showNotification() {
        notificationShown = true

        val builder = createNotification()
        notificationManager.notify(AppConstants.NOTIFICATION_ID_LIVE_MAP, builder.build())
    }

    private fun hideNotification() {
        notificationShown = false

        val pendingIntent = createPendingIntent(ACTION_HIDE_NOTIFICATION)

        alarmManager.cancel(pendingIntent)
        notificationManager.cancel(AppConstants.NOTIFICATION_ID_LIVE_MAP)
    }

    private fun createNotification(): NotificationCompat.Builder = NotificationCompat.Builder(context, LIVE_MAP_CHANNEL).apply {
        setOngoing(true)
        setWhen(0) // this fix redraw issue while refreshing
        setLocalOnly(true)
        setCategory(NotificationCompat.CATEGORY_SERVICE)

        val state: CharSequence
        if (enable) {
            setSmallIcon(R.drawable.ic_stat_oc_map)
            state = context.getText(R.string.notify_live_map_message_enabled)
            addAction(R.drawable.ic_pause_black_24dp, context.getText(R.string.notify_live_map_action_disable), createPendingIntent(ACTION_LIVE_MAP_DISABLE))
        } else {
            setSmallIcon(R.drawable.ic_stat_oc_map_disabled)
            state = context.getText(R.string.notify_live_map_message_disabled)
            addAction(R.drawable.ic_play_arrow_black_24dp, context.getText(R.string.notify_live_map_action_enable), createPendingIntent(ACTION_LIVE_MAP_ENABLE))
        }
        val pendingIntent = PendingIntent.getActivity(context, 0,
                SettingsActivity.createIntent(context, LiveMapPreferenceFragment::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT)
        addAction(R.drawable.ic_settings_black_48dp, context.getText(R.string.notify_live_map_action_settings), pendingIntent)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            setContentTitle(context.getText(R.string.notify_live_map))
            setContentText(state)
        } else {
            setSubText(context.getText(R.string.dashboard_live_map))
            setContentTitle(state)
        }
        priority = NotificationCompat.PRIORITY_MAX // always show button
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(LIVE_MAP_CHANNEL, context.getText(R.string.notify_live_map_channel_name), NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = context.getString(R.string.notify_live_map_channel_description)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
    }

    private fun createPendingIntent(intentAction: String): PendingIntent {
        val intent = Intent(context, LiveMapBroadcastReceiver::class.java).apply {
            action = intentAction
        }

        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    fun handleError(throwable: Throwable) {
        context.showError {
            message(throwable.message ?: "")
        }

        if (throwable is OkApiException)
            enable = false
    }

    fun showProgress(current: Int, count: Int) {
        if (!notificationShown)
            return

        val nb = createNotification()

        if (current == 0) {
            nb.setProgress(0, 0, true)
        } else if (current < count) {
            nb.setProgress(count, current, false)
        }

        if (current < count) {
            nb.setSmallIcon(R.drawable.ic_stat_oc_map_downloading)
            val content = context.getText(R.string.notify_live_map_message_downloading)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                nb.setContentText(content)
            } else {
                nb.setContentTitle(content)
            }
        }

        notificationManager.notify(AppConstants.NOTIFICATION_ID_LIVE_MAP, nb.build())
    }
}