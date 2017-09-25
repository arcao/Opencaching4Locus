package com.arcao.opencaching4locus.ui.livemap.manager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.arcao.opencaching4locus.ui.base.constants.PrefConstants
import javax.inject.Inject


class LiveMapNotificationManager @Inject constructor(
        private val context: Context,
        private val sharedPreferences: SharedPreferences
) {
    var enable: Boolean
        get() = sharedPreferences.getBoolean(PrefConstants.LIVE_MAP, PrefConstants.LIVE_MAP_DEFAULT)
        set(value) { sharedPreferences.edit().putBoolean(PrefConstants.LIVE_MAP, value).apply() }

    fun handleBroadcastIntent(intent: Intent): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isForceUpdateRequiredInFuture(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showError(throwable: Throwable) {
        Toast.makeText(context, throwable.message ?: "", Toast.LENGTH_LONG).show()
    }

}