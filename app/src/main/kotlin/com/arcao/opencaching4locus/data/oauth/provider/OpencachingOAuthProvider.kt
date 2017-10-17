package com.arcao.opencaching4locus.data.oauth.provider

import com.arcao.opencaching4locus.data.okapi.OkApiServiceType
import com.github.scribejava.core.builder.api.DefaultApi10a
import com.github.scribejava.core.model.OAuth1RequestToken
import com.github.scribejava.core.utils.OAuthEncoder

open class OpencachingOAuthProvider(serviceType: OkApiServiceType) : DefaultApi10a() {
    private val requestTokenEndpoint = "${serviceType.endpoint}services/oauth/request_token"
    private val accessTokenEndpoint = "${serviceType.endpoint}services/oauth/access_token"
    private val authorizationUrl = "${serviceType.endpoint}services/oauth/authorize"

    override fun getRequestTokenEndpoint(): String = requestTokenEndpoint

    override fun getAccessTokenEndpoint(): String = accessTokenEndpoint

    override fun getAuthorizationUrl(requestToken: OAuth1RequestToken): String = authorizationUrl + "?interactivity=confirm_user&oauth_token=" + OAuthEncoder.encode(requestToken.token)
}
