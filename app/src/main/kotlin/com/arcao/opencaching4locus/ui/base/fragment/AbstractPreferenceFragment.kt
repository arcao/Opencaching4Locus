package com.arcao.opencaching4locus.ui.base.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.text.Html
import com.arcao.opencaching4locus.R
import util.orDefault
import org.oshkimaadziig.george.androidutils.SpanFormatter

abstract class AbstractPreferenceFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    protected lateinit var mPreferences: SharedPreferences

    protected fun <P : Preference> findPreference(key: CharSequence, clazz: Class<P>): P {
        @Suppress("UNCHECKED_CAST")
        return super.findPreference(key) as P
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        // empty
    }

    override fun onCreate(paramBundle: Bundle?) {
        super.onCreate(paramBundle)
        mPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        preparePreference()
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    protected fun preparePreference() {
        if (!resources.getBoolean(R.bool.preferences_prefer_dual_pane))
            activity.title = preferenceScreen.title
    }

    protected fun preparePreferenceSummary(value: CharSequence?, resId: Int): CharSequence {
        var summary: CharSequence? = null
        if (resId != 0)
            summary = getText(resId)

        if (value != null && value.isNotEmpty())
            return SpanFormatter.format("%s %s", stylizedValue(value), summary.orDefault())
        return summary.orDefault()
    }

    private fun stylizedValue(value: CharSequence): CharSequence {
        return SpanFormatter.format(Html.fromHtml(VALUE_HTML_FORMAT), value)
    }

    companion object {
        val VALUE_HTML_FORMAT = "<font color=\"#FF8000\"><b>%s</b></font>"
    }
}
