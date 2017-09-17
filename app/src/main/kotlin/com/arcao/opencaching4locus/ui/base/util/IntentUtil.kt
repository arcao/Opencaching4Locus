package com.arcao.opencaching4locus.ui.base.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Activity?.showWebPage(uri: Uri): Boolean {
    if (this == null)
        return false

    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    }

    return if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        true
    } else {
        Toast.makeText(this, "Web page cannot be opened. No application found to handle web pages.", Toast.LENGTH_LONG).show()
        false
    }
}

