package com.arcao.opencaching4locus.ui.authentication.util

import android.app.Activity
import com.arcao.opencaching4locus.data.account.AccountManager
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.ui.authentication.AuthenticationActivity
import com.arcao.opencaching4locus.ui.base.constants.AppConstants


fun Activity.requestSignIn(accountType: AccountType, requestCode: Int = AppConstants.REQUEST_SIGN_IN): Boolean {
    return if (!AccountManager.get(this).authenticated(accountType)) {
        startActivityForResult(AuthenticationActivity.createIntent(this, accountType), requestCode)
        true
    } else {
        false
    }
}