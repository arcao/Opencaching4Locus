package com.arcao.opencaching4locus.ui.settings.fragment

import android.os.Bundle
import android.preference.Preference
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.account.AccountManager
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.ui.base.fragment.BasePreferenceFragment
import com.arcao.opencaching4locus.ui.base.util.getText
import com.arcao.opencaching4locus.ui.authentication.util.requestSignIn
import javax.inject.Inject

class AccountsPreferenceFragment : BasePreferenceFragment() {
    @Inject internal lateinit var accountManager: AccountManager

    override fun onCreate(paramBundle: Bundle?) {
        super.onCreate(paramBundle)

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference_category_accounts)
    }

    override fun preparePreference() {
        super.preparePreference()

        preferenceScreen.removeAll()

        AccountType.values().forEach {
            preferenceScreen.addPreference(createAccountPreference(accountManager.getAccount(it)))
        }
    }

    private fun createAccountPreference(account: Account): Preference = Preference(activity).apply {
        title = account.accountType.accountName
        summary = prepareAccountSummary(account)

        onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (account.authenticated) {
                account.remove()
                summary = prepareAccountSummary(account)
            } else {
                activity.requestSignIn(account.accountType)
            }
            true
        }
    }

    private fun prepareAccountSummary(account: Account): CharSequence = if (account.authenticated) {
        getText(R.string.preference_accounts_logout_summary, stylizedValue(account.userName!!))
    } else {
        getText(R.string.preference_accounts_login_summary)
    }
}
