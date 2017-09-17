package com.arcao.opencaching4locus.ui.base.constants

import locus.api.android.utils.LocusUtils

interface AppConstants {
    companion object {
        const val LOCUS_MIN_VERSION = "3.8.0"
        val LOCUS_MIN_VERSION_CODE = LocusUtils.VersionCode.UPDATE_09

        const val OAUTH_CALLBACK_URL = "x-oc4l://oauth.callback/"
    }
}
