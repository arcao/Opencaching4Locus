package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.account.AccountType

interface OkApiService {
    fun getAccountType() : AccountType
    fun getServiceType() : OkApiServiceType
    fun getApi() : OkApi
    fun isAuthenticated() : Boolean
}

