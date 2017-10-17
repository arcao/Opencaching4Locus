package com.arcao.opencaching4locus.ui.base.constants

import android.content.Intent
import android.net.Uri
import locus.api.android.utils.LocusUtils

object AppConstants {
    const val LOCUS_MIN_VERSION = "3.8.0"
    val LOCUS_MIN_VERSION_CODE = LocusUtils.VersionCode.UPDATE_09
    val LOCUS_GOOGLE_PLAY_INTENT : Intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://market.android.com/details?id=menion.android.locus"))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    const val OAUTH_CALLBACK_URL = "https://martinsloup.cz/projects/opencaching4locus"
    const val NOTIFICATION_ID_LIVE_MAP: Int = 1
    const val REQUEST_SIGN_IN: Int = 100
}
