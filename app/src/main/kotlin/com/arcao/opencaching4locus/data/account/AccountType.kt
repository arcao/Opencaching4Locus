package com.arcao.opencaching4locus.data.account

import com.arcao.opencaching4locus.data.okapi.OkApiServiceType

enum class AccountType(val accountName: String) {
    OPENCACHING_PL("Opencaching.PL"),
    OPENCACHING_DE("Opencaching.DE"),
    OPENCACHING_US("Opencaching.US"),
    OPENCACHING_NL("Opencaching.NL"),
    OPENCACHING_RO("Opencaching.RO"),
    OPENCACHING_UK("Opencaching.UK");

    fun toServiceType() : OkApiServiceType = OkApiServiceType.from(this)
}