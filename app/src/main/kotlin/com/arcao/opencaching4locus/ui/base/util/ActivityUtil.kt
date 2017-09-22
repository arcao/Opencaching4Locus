package com.arcao.opencaching4locus.ui.base.util

import android.app.Activity

fun Activity.showError(throwable: Throwable) {
    showError(throwable.message ?: throwable::class.simpleName!!)
}

fun Activity.showError(message: String) {

}