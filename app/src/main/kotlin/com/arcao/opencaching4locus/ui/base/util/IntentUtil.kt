package com.arcao.opencaching4locus.ui.base.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object IntentUtil {
    fun showWebPage(activity: Activity?, uri: Uri): Boolean {
        if (activity == null)
            return false

        val intent = Intent(Intent.ACTION_VIEW, uri)


        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        return if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
            true
        } else {
            Toast.makeText(activity, "Web page cannot be opened. No application found to handle web pages.", Toast.LENGTH_LONG).show()
            false
        }
    }
}
