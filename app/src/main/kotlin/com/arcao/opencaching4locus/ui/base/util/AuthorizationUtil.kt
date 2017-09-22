package com.arcao.opencaching4locus.ui.base.util

import android.app.Activity
import com.arcao.opencaching4locus.data.account.AccountManager
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.ui.authorization.AuthorizationActivity


fun Activity.requestSignIn(accountType: AccountType, requestCode: Int = 0): Boolean {
    return if (!AccountManager.get(this).authenticated(accountType)) {
        startActivityForResult(AuthorizationActivity.createIntent(this, accountType), requestCode)
        true
    } else {
        false
    }
}