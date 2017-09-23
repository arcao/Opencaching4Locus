package com.arcao.opencaching4locus.ui.authentication

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.databinding.ActivityAuthenticationBinding
import com.arcao.opencaching4locus.ui.base.BaseActivity
import com.arcao.opencaching4locus.ui.base.constants.AppConstants
import com.arcao.opencaching4locus.ui.base.util.showError
import javax.inject.Inject

class AuthenticationActivity : BaseActivity() {
    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var accountType: AccountType

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountType = intent.getSerializableExtra(EXTRA_ACCOUNT_TYPE) as AccountType

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean =
                        checkRequest(request)

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    showProgress()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    hideProgress()
                }
            }
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthenticationViewModel::class.java)
        viewModel.authorizationState.observe(this, Observer {
            when (it) {
                is AuthenticationStarted, AccessTokenRequestSent -> showProgress()
                is RequestTokenReceived -> openAuthorizeUrl(it.url)
                is AuthenticationError -> showError(it.throwable)
                is AuthenticationSuccess -> {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        })

        if (savedInstanceState == null)
            viewModel.retrieveRequestToken(accountType)
    }

    private fun showProgress() {
        binding.progressHolder.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressHolder.visibility = View.GONE
    }

    private fun openAuthorizeUrl(url: String) {
        binding.webView.loadUrl(url)
    }

    private fun checkRequest(request: WebResourceRequest): Boolean {
        if (request.url.toString().startsWith(AppConstants.OAUTH_CALLBACK_URL)) {
            viewModel.retrieveAccessToken(accountType, request.url)
            return true
        }

        return false
    }

    companion object {
        const val EXTRA_ACCOUNT_TYPE = "ACCOUNT_TYPE"
        fun createIntent(context: Context, accountType: AccountType): Intent = Intent(context, AuthenticationActivity::class.java).apply {
            putExtra(EXTRA_ACCOUNT_TYPE, accountType)
        }
    }
}
