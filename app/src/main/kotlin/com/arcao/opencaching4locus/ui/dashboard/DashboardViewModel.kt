package com.arcao.opencaching4locus.ui.dashboard

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.ObservableField
import com.arcao.opencaching4locus.ui.base.constants.PrefConstants
import com.arcao.opencaching4locus.ui.livemap.manager.LiveMapNotificationManager
import com.arcao.opencaching4locus.ui.settings.SettingsActivity
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
        private val context: Context,
        private val liveMapNotificationManager: LiveMapNotificationManager,
        private val sharedPreferences: SharedPreferences
) : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {
    var intent: Intent? = null
    val liveMapEnabled = ObservableField<Boolean>(sharedPreferences.getBoolean(PrefConstants.LIVE_MAP, PrefConstants.LIVE_MAP_DEFAULT))
    val startActivityLiveData = MutableLiveData<Intent>()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCleared() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onCleared()
    }

    fun toggleLiveMap() {
        liveMapNotificationManager.enable = !liveMapNotificationManager.enable
    }

    fun showSettings() {
        startActivityLiveData.value = SettingsActivity.createIntent(context)
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences, key: String) {
        when (key) {
            PrefConstants.LIVE_MAP -> liveMapEnabled.set(sharedPreferences.getBoolean(key, PrefConstants.LIVE_MAP_DEFAULT))
        }
    }


}