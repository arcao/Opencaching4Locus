package com.arcao.opencaching4locus.data.okapi

import com.arcao.opencaching4locus.data.DataModule
import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.data.oauth.provider.OpencachingOAuthProvider
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.oauth.OAuth10aService
import com.nhaarman.mockito_kotlin.mock
import java.io.BufferedReader
import java.io.InputStreamReader


class OkApiSignTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val service = createOAuthService()

            println("Fetching request token from OpencachingPL...")
            val requestToken = service.requestToken

            println("Request token: " + requestToken.token)
            println("Token secret: " + requestToken.tokenSecret)

            val authUrl = service.getAuthorizationUrl(requestToken)

            println("Now visit:\n$authUrl\n... and grant this app authorization")

            // after you allow the access you'll get move to URL like (callback url + authorization parameters):
            // e.g.: http://oauth.callback/callback?oauth_verifier=...&oauth_token=...
            // put oauth_verifier bellow (beware! must be URL decoded)
            println("Enter the oauth_verifier and hit ENTER when you're done:")

            val br = BufferedReader(InputStreamReader(System.`in`))
            val oAuthVerifier = br.readLine()

            println("Fetching access token from Opencaching...")

            val accessToken = service.getAccessToken(requestToken, oAuthVerifier)

            println("Access token: " + accessToken.token)
            println("Token secret: " + accessToken.tokenSecret)

            val account = mock<Account> {
                on { accessToken() }.thenReturn(accessToken.token)
                on { accessSecret() }.thenReturn(accessToken.tokenSecret)
                on { accountType }.thenReturn(AccountType.OPENCACHING_PL)
                on { authenticated() }.thenReturn(true)
            }

            val okApiModule = OkApiModule()
            val dataModule = DataModule()

            val okHttpClient = dataModule.provideOkHttpClient()
            val retrofitBuilder = dataModule.provideRetrofitBuilder(dataModule.provideMoshi())

            val okService = okApiModule.provideOkApiServicesPL(account, okHttpClient, retrofitBuilder)

            println(okService.user())
        }

        private fun createOAuthService(): OAuth10aService {
            val serviceType = OkApiServiceType.OPENCACHING_PL

            val serviceBuilder = ServiceBuilder(serviceType.consumerKey)
                    .apiSecret(serviceType.consumerSecret)
                    .callback("http://test.arcao.com/oauth.php")
                    .debug()

            // For staging server has to be used staging OAuth service
            return serviceBuilder.build(OpencachingOAuthProvider(serviceType))
        }
    }
}