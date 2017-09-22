package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.BuildConfig
import com.arcao.opencaching4locus.data.account.AccountType

enum class OkApiServiceType(val serviceName: String, val endpoint: String, val consumerKey: String = "", val consumerSecret: String = "") {
    // HTTPS OAuth is broken, use http protocol instead
    OPENCACHING_PL("Opencaching.PL", "http://www.opencaching.pl/okapi/", BuildConfig.OC_PL_CONSUMER_KEY, BuildConfig.OC_PL_CONSUMER_SECRET),
    OPENCACHING_DE("Opencaching.DE", "http://www.opencaching.de/okapi/"),
    OPENCACHING_US("Opencaching.US", "http://www.opencaching.us/okapi/"),
    OPENCACHING_NL("Opencaching.NL", "http://www.opencaching.nl/okapi/"),
    OPENCACHING_RO("Opencaching.RO", "http://www.opencaching.ro/okapi/"),
    OPENCACHING_UK("Opencaching.UK", "http://www.opencaching.uk/okapi/");

    companion object {
        fun from(accountType: AccountType) : OkApiServiceType = when(accountType) {
            AccountType.OPENCACHING_PL -> OPENCACHING_PL
            AccountType.OPENCACHING_DE -> OPENCACHING_DE
            AccountType.OPENCACHING_US -> OPENCACHING_US
            AccountType.OPENCACHING_NL -> OPENCACHING_NL
            AccountType.OPENCACHING_RO -> OPENCACHING_RO
            AccountType.OPENCACHING_UK -> OPENCACHING_UK
        }
    }
}