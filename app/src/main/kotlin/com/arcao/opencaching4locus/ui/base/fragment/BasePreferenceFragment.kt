package com.arcao.opencaching4locus.ui.base.fragment

import android.app.Activity
import android.app.Fragment
import android.content.SharedPreferences
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.annotation.CallSuper
import android.text.Html
import com.arcao.opencaching4locus.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import org.oshkimaadziig.george.androidutils.SpanFormatter
import util.isNotNullOrEmpty
import util.orDefault
import javax.inject.Inject

abstract class BasePreferenceFragment : PreferenceFragment(), HasFragmentInjector, SharedPreferences.OnSharedPreferenceChangeListener {
    companion object {
        const val VALUE_HTML_FORMAT = "<font color=\"#FF8000\"><b>%s</b></font>"
    }

    @Inject internal open lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected open lateinit var sharedPreferences: SharedPreferences

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        AndroidInjection.inject(this)
        super.onAttach(activity)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) =// empty
            Unit

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        preparePreference()
    }

    override fun onPause() {
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    @CallSuper
    internal open fun preparePreference() {
        if (!resources.getBoolean(R.bool.preferences_prefer_dual_pane))
            activity.title = preferenceScreen.title
    }

    internal fun preparePreferenceSummary(value: CharSequence?, resId: Int): CharSequence {
        var summary: CharSequence? = null
        if (resId != 0)
            summary = getText(resId)

        if (value.isNotNullOrEmpty())
            return SpanFormatter.format("%s %s", stylizedValue(value!!), summary.orDefault())
        return summary.orDefault()
    }

    internal fun stylizedValue(value: CharSequence): CharSequence {
        @Suppress("DEPRECATION") // since sdk24
        return SpanFormatter.format(Html.fromHtml(VALUE_HTML_FORMAT), value)
    }
}
