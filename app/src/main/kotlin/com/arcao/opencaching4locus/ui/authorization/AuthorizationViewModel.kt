package com.arcao.opencaching4locus.ui.authorization

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.arcao.opencaching4locus.data.account.AccountManager
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.data.oauth.provider.OpencachingOAuthProvider
import com.arcao.opencaching4locus.data.okapi.OkApiService
import com.arcao.opencaching4locus.data.okapi.OkApiServiceType
import com.arcao.opencaching4locus.ui.base.constants.AppConstants
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.oauth.OAuth10aService
import com.github.scribejava.httpclient.okhttp.OkHttpHttpClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
        private val services: Map<OkApiServiceType, @JvmSuppressWildcards OkApiService>,
        private val okhttpClient : OkHttpClient,
        private val accountManager : AccountManager
) : ViewModel() {

    companion object {
        private const val OAUTH_VERIFIER = "oauth_verifier"
    }

    val authorizationState = MutableLiveData<AuthorizationState>()
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun retrieveRequestToken(accountType: AccountType) {
        val serviceType = accountType.toServiceType()
        val oauthService = createOAuthService(serviceType)

        // clean state
        authorizationState.value = AuthorizationStarted

        Single.fromCallable<String> {
            val requestToken = oauthService.requestToken
            accountManager.getAccount(accountType).requestToken = requestToken
            oauthService.getAuthorizationUrl(requestToken)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                { authorizationState.value = AuthorizationRequired(it) },
                { authorizationState.value = AuthorizationError(it) }
        ).addTo(disposables)
    }

    fun retrieveAccessToken(accountType: AccountType, url: Uri) {
        val serviceType = accountType.toServiceType()
        val oauthService = createOAuthService(serviceType)
        val account = accountManager.getAccount(accountType)

        Single.fromCallable {
            val okService = services[serviceType]!!
            val requestToken = account.requestToken
            val accessToken = oauthService.getAccessToken(requestToken, url.getQueryParameter(OAUTH_VERIFIER))

            account.authorize(accessToken)
            account.updateUser(okService.user().blockingGet())
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                { authorizationState.value = AuthorizationSuccess },
                { authorizationState.value = AuthorizationError(it) }
        ).addTo(disposables)
    }

    private fun createOAuthService(serviceType: OkApiServiceType): OAuth10aService {
        val serviceBuilder = ServiceBuilder(serviceType.consumerKey)
                .apiSecret(serviceType.consumerSecret)
                .callback(AppConstants.OAUTH_CALLBACK_URL)
                .httpClient(OkHttpHttpClient(okhttpClient))
                .debug()

        return serviceBuilder.build(OpencachingOAuthProvider(serviceType))
    }
}