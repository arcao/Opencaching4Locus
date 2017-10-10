package com.arcao.opencaching4locus.ui.settings.fragment

import android.os.Bundle
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.data.account.AccountManager
import com.arcao.opencaching4locus.ui.base.fragment.BasePreferenceFragment
import javax.inject.Inject

class LiveMapPreferenceFragment : BasePreferenceFragment() {
    @Inject internal lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference_category_live_map)
    }
}