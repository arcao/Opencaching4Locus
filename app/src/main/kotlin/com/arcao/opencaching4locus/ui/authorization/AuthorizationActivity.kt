package com.arcao.opencaching4locus.ui.authorization

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.data.account.AccountType
import com.arcao.opencaching4locus.databinding.ActivityAuthorizationBinding
import com.arcao.opencaching4locus.ui.base.BaseActivity
import com.arcao.opencaching4locus.ui.base.constants.AppConstants
import com.arcao.opencaching4locus.ui.base.util.showError
import javax.inject.Inject

class AuthorizationActivity : BaseActivity() {
    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthorizationViewModel
    private lateinit var binding: ActivityAuthorizationBinding
    private lateinit var accountType: AccountType

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountType = intent.getSerializableExtra(EXTRA_ACCOUNT_TYPE) as AccountType

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authorization)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean =
                        checkRequest(request)
            }
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AuthorizationViewModel::class.java)
        viewModel.authorizationState.observe(this, Observer {
            when (it) {
                is AuthorizationRequired -> binding.webView.loadUrl(it.url)
                is AuthorizationError -> showError(it.throwable)
                is AuthorizationSuccess -> {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        })

        if (savedInstanceState == null)
            viewModel.retrieveRequestToken(accountType)
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
        fun createIntent(context: Context, accountType: AccountType): Intent = Intent(context, AuthorizationActivity::class.java).apply {
            putExtra(EXTRA_ACCOUNT_TYPE, accountType)
        }
    }
}
