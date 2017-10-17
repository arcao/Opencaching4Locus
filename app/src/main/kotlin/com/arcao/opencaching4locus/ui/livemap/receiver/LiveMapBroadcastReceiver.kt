package com.arcao.opencaching4locus.ui.livemap.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.arcao.opencaching4locus.ui.livemap.LiveMapJobService
import com.arcao.opencaching4locus.ui.livemap.manager.LiveMapNotificationManager
import dagger.android.AndroidInjection
import locus.api.android.features.periodicUpdates.PeriodicUpdatesHandler
import locus.api.android.features.periodicUpdates.UpdateContainer
import locus.api.android.utils.LocusUtils
import javax.inject.Inject


class LiveMapBroadcastReceiver : BroadcastReceiver() {
    @Inject internal lateinit var notificationManager : LiveMapNotificationManager

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null || intent.action == null)
            return

        AndroidInjection.inject(this, context)

        if (notificationManager.handleBroadcastIntent(intent))
            return

        if (!notificationManager.enable) {
            return
        }

        // ignore onTouch events
        if (intent.getBooleanExtra(VAR_B_MAP_USER_TOUCHES, false))
            return

        // TODO necessary?
        // temporary fix for NPE bug (locMapCenter can be null)
        if (LocusUtils.getLocationFromIntent(intent, VAR_LOC_MAP_CENTER) == null)
            return

        // get valid instance of PeriodicUpdate object
        val pu = PeriodicUpdatesHandler.getInstance()

        // set notification of new locations
        pu.setLocNotificationLimit(computeNotificationLimit(intent).toDouble())

        // handle event
        pu.onReceive(context, intent, object : PeriodicUpdatesHandler.OnUpdate {
            override fun onIncorrectData() = Unit

            override fun onUpdate(locusVersion: LocusUtils.LocusVersion, update: UpdateContainer) {
                // sending data back to Locus based on events if has a new map center or zoom level and map is visible
                if (!update.isMapVisible)
                    return

                if (!update.isNewMapCenter && !update.isNewZoomLevel && !notificationManager.forceUpdateRequired)
                    return

                // TODO necessary?
                // When Live map is enabled, Locus sometimes send NaN when is starting
                if (update.mapTopLeft.getLatitude().isNaN() || update.mapTopLeft.getLongitude().isNaN()
                        || update.mapBottomRight.getLatitude().isNaN() || update.mapBottomRight.getLongitude().isNaN())
                    return

                if (update.mapTopLeft.distanceTo(update.mapBottomRight) >= MAX_DIAGONAL_DISTANCE) {
                    // TODO show toast here
                    return  // Zoom is too low
                }

                //val l = update.locMapCenter

                // Create job to download caches
                LiveMapJobService.createNewJob(context, update.mapTopLeft, update.mapBottomRight)
            }
        })
    }

    companion object {
        private const val VAR_B_MAP_USER_TOUCHES = "1306"
        private const val VAR_LOC_MAP_CENTER = "1302"
        private const val VAR_LOC_MAP_BBOX_TOP_LEFT = "1303"

        private const val MAX_DIAGONAL_DISTANCE = 400000f

        private const val DEFAULT_DISTANCE_LIMIT = 100f
        private const val DISTANCE_LIMIT_MULTIPLIER = 0.5f

        private fun computeNotificationLimit(i: Intent): Float {
            val locMapCenter = LocusUtils.getLocationFromIntent(i, VAR_LOC_MAP_CENTER)
            val mapTopLeft = LocusUtils.getLocationFromIntent(i, VAR_LOC_MAP_BBOX_TOP_LEFT)

            return if (locMapCenter == null || mapTopLeft == null) DEFAULT_DISTANCE_LIMIT else mapTopLeft.distanceTo(locMapCenter) * DISTANCE_LIMIT_MULTIPLIER

        }
    }
}
