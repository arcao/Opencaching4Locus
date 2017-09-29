package com.arcao.opencaching4locus.ui.error.util

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun Activity.showError(resultCode: Int = 0, init: ErrorIntentBuilder.() -> Unit) {
    startActivityForResult(ErrorIntentBuilder(this).apply(init).build(), resultCode)
}

inline fun Context.showError(init: ErrorIntentBuilder.() -> Unit) {
    startActivity(ErrorIntentBuilder(this).apply(init).build().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}
