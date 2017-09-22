package com.arcao.opencaching4locus.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceScreen
import android.support.annotation.XmlRes
import android.view.MenuItem
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.ui.base.BasePreferenceActivity
import java.util.*

class SettingsActivity : BasePreferenceActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // hack / fix for Samsung phones - missing padding in header layout
        if (!onIsMultiPane() && !intent.hasExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT)) {
            preferenceScreen = getPreferenceScreenFromHeader(R.xml.preference_header)
        }
    }

    override fun onIsMultiPane(): Boolean = resources.getBoolean(R.bool.preferences_prefer_dual_pane)
    override fun isValidFragment(fragmentName: String): Boolean = true

    /**
     * Populate the activity with the top-level headers.
     */
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        if (onIsMultiPane()) {
            loadHeadersFromResource(R.xml.preference_header, target)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
    // Respond to the action bar's Up/Home button
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun getPreferenceScreenFromHeader(@XmlRes headerRes: Int): PreferenceScreen {
        val headers = ArrayList<PreferenceActivity.Header>()
        loadHeadersFromResource(headerRes, headers)

        val preferenceScreen = preferenceManager.createPreferenceScreen(this)
        headers.map { header ->
            Preference(this).apply {
                setTitle(header.titleRes)
                // HACK: with setFragment does not work click
                intent = onBuildStartFragmentIntent(header.fragment, null, header.titleRes, 0)
            }
        }.forEach { preferenceScreen.addPreference(it) }

        return preferenceScreen
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }

        fun createIntent(context: Context, preferenceFragment: String): Intent {
            return createIntent(context).putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, preferenceFragment)
        }

        fun createIntent(context: Context, preferenceFragment: Class<Any>): Intent {
            return createIntent(context, preferenceFragment.name)
        }
    }
}
