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
        val okApiModule = OkApiModule()

        val dataModule = DataModule()
        val okHttpClient = dataModule.provideOkHttpClient()
        val moshi = dataModule.provideMoshi()
        val retrofitBuilder = dataModule.provideRetrofitBuilder(moshi)

        val account = mock<Account> {
            on { authenticated } doReturn (false)
        }

        service = okApiModule.provideOkApiServicesPL(account, okHttpClient, retrofitBuilder)
    }

    @Test
    fun shouldFindNearestGeocaches() {
        val geocachesFlowable = service.nearestGeocaches(Location(50.0, 14.0), 100)

        val geocaches = geocachesFlowable.toList().blockingGet()
        geocaches.size shouldEqual 100
    }
}