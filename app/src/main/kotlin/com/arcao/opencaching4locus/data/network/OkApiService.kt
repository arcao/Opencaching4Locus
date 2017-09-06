package com.arcao.opencaching4locus.data.network

interface OkApiService {
    fun getServiceType() : OkApiServiceType
    fun getApi() : OkApi
    fun authorized() : Boolean
}

