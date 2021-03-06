package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.DataModule
import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.model.Location
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test


class OkApiServicePLTest {
    lateinit var service: OkApiService

    @Before
    fun setup() {
        val okHttpClient = DataModule.provideOkHttpClient()
        val moshi = DataModule.provideMoshi()
        val retrofitBuilder = DataModule.provideRetrofitBuilder(moshi)

        val account = mock<Account> {
            on { authenticated } doReturn (false)
        }

        service = OkApiModule.provideOkApiServicesPL(account, okHttpClient, retrofitBuilder)
    }

    @Test
    fun shouldFindNearestGeocaches() {
        val geocachesFlowable = service.nearestGeocaches(Location(50.0, 14.0), 100)

        val geocaches = geocachesFlowable.toList().blockingGet()
        geocaches.size shouldEqual 100
    }
}