package com.arcao.opencaching4locus.data.locusmap.util

import android.content.Context
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.ui.base.constants.AppConstants
import com.arcao.opencaching4locus.ui.base.util.getText
import com.arcao.opencaching4locus.ui.error.util.showError
import locus.api.android.ActionTools
import locus.api.android.utils.LocusUtils
import timber.log.Timber

fun Context.checkLocusMap() : Boolean {
    val lv = LocusUtils.getActiveVersion(this)
    val locusVersion = if (lv != null) lv.versionName else ""
    Timber.v("Locus version: " + locusVersion + "; Required version: " + AppConstants.LOCUS_MIN_VERSION)

    if (lv == null) {
        // Locus Map is not installed
        showError {
            message(R.string.error_locus_not_found)
            positiveButton(R.string.button_open_google_play, AppConstants.LOCUS_GOOGLE_PLAY_INTENT)
            negativeButton(R.string.button_cancel)
        }
        return false
    }

    if (!lv.isVersionValid(AppConstants.LOCUS_MIN_VERSION_CODE)) {
        // Locus Map is too old
        showError {
            message(getText(R.string.error_old_locus_map, AppConstants.LOCUS_MIN_VERSION))
            positiveButton(R.string.button_open_google_play, AppConstants.LOCUS_GOOGLE_PLAY_INTENT)
            negativeButton(R.string.button_cancel)
        }
        return false
    }

    return true
}

fun Context.isPeriodicUpdatesEnabled(): Boolean {
    val locusVersion = LocusUtils.getActiveVersion(this)

    try {
        val info = ActionTools.getLocusInfo(this, locusVersion)
        if (info != null) {
            return info.isPeriodicUpdatesEnabled
        }
    } catch (e: Throwable) {
        Timber.e(e, "Unable to receive info about current state of periodic update events from Locus.")
    }
    return true
}
