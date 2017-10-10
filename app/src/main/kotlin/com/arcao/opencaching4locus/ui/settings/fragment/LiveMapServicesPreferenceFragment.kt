package com.arcao.opencaching4locus.ui.settings.fragment

import android.content.Context
import android.os.Bundle
import android.preference.CheckBoxPreference
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.account.AccountManager
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.ui.base.fragment.BasePreferenceFragment
import javax.inject.Inject

class LiveMapServicesPreferenceFragment : BasePreferenceFragment() {
    @Inject internal lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference_category_live_map_services)
    }

    override fun preparePreference() {
        super.preparePreference()

        preferenceScreen.removeAll()
        AccountType.values().forEach {
            preferenceScreen.addPreference(
                    CheckBoxServicePreference(activity, accountManager.getAccount(it))
            )
        }
    }

    class CheckBoxServicePreference(context: Context, private val account: Account) : CheckBoxPreference(context) {
        init {
            title = account.accountType.name
        }

        override fun persistBoolean(value: Boolean): Boolean {
            account.liveMapEnabled = value
            return true
        }

        override fun getPersistedBoolean(defaultReturnValue: Boolean): Boolean = account.liveMapEnabled
    }
}