package com.arcao.opencaching4locus.ui.base.util

import android.content.Context
import locus.api.android.ActionTools
import locus.api.android.utils.LocusUtils
import timber.log.Timber

fun Context.checkLocusMap() : Boolean {
    return false
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
