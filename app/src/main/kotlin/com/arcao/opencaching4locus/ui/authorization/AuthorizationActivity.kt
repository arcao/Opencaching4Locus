package com.arcao.opencaching4locus.ui.authorization

import android.app.AlertDialog
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.data.oauth.provider.OpencachingOAuthProvider
import com.arcao.opencaching4locus.data.okapi.OkApiService
import com.arcao.opencaching4locus.data.okapi.OkApiServiceType
import com.arcao.opencaching4locus.databinding.ActivityAuthorizationBinding
import com.arcao.opencaching4locus.ui.base.BaseActivity
import com.arcao.opencaching4locus.ui.base.constants.AppConstants
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth1AccessToken
import com.github.scribejava.core.model.OAuth1RequestToken
import com.github.scribejava.core.oauth.OAuth10aService
import com.github.scribejava.httpclient.okhttp.OkHttpHttpClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import javax.inject.Inject

class AuthorizationActivity : BaseActivity() {
    private val OAUTH_VERIFIER = "oauth_verifier"

    @Inject
    lateinit var services: Map<OkApiServiceType, @JvmSuppressWildcards OkApiService>

    @Inject
    lateinit var okhttpClient : OkHttpClient

    private lateinit var oauthService: OAuth10aService
    private lateinit var requestToken: OAuth1RequestToken
    private lateinit var accessToken: OAuth1AccessToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityAuthorizationBinding>(this, R.layout.activity_authorization)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                if (request.url.toString().startsWith(AppConstants.OAUTH_CALLBACK_URL)) {
                    retrieveAccessToken(request.url)
                    return true
                }

                return false
            }
        }

        retrieveRequestToken(binding.webView)
    }

    private fun retrieveRequestToken(webView: WebView) {
        oauthService = createOAuthService()

        Single.fromCallable<String> {
            requestToken = oauthService.requestToken
            oauthService.getAuthorizationUrl(requestToken)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { url: String ->
            webView.loadUrl(url)
        }
    }

    private fun retrieveAccessToken(url: Uri) {
        Single.fromCallable {
            accessToken = oauthService.getAccessToken(requestToken, url.getQueryParameter(OAUTH_VERIFIER))
            val okService = services[OkApiServiceType.OPENCACHING_PL]
            okService?.user()?.blockingGet().toString()


        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { user: String? ->
            AlertDialog.Builder(this).apply {
                setMessage(user ?: "")
            }.show()
        }
    }

    private fun createOAuthService(): OAuth10aService {
        val serviceType = OkApiServiceType.OPENCACHING_PL

        val serviceBuilder = ServiceBuilder(serviceType.consumerKey)
                .apiSecret(serviceType.consumerSecret)
                .callback(AppConstants.OAUTH_CALLBACK_URL)
                .httpClient(OkHttpHttpClient(okhttpClient))
                .debug()

        // For staging server has to be used staging OAuth service
        return serviceBuilder.build(OpencachingOAuthProvider(serviceType))
    }
}