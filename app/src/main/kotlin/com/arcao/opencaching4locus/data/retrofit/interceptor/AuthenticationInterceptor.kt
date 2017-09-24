package com.arcao.opencaching4locus.data.retrofit.interceptor

import com.arcao.opencaching4locus.data.account.Account
import com.arcao.opencaching4locus.data.okapi.OkApiServiceType
import com.github.scribejava.core.utils.OAuthEncoder
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import okio.ByteString
import java.io.IOException
import java.security.SecureRandom
import java.util.*


class AuthenticationInterceptor(private val account: Account, private val serviceType: OkApiServiceType) : Interceptor {
    companion object {
        private const val OAUTH_CONSUMER_KEY = "oauth_consumer_key"
        private const val OAUTH_NONCE = "oauth_nonce"
        private const val OAUTH_SIGNATURE = "oauth_signature"
        private const val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
        private const val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
        private const val OAUTH_TIMESTAMP = "oauth_timestamp"
        private const val OAUTH_ACCESS_TOKEN = "oauth_token"
        private const val OAUTH_VERSION = "oauth_version"
        private const val OAUTH_VERSION_VALUE = "1.0"
    }

    private val random = SecureRandom()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        return if (account.authenticated) {
            chain.proceed(oAuthSignRequest(original, serviceType, account))
        } else {
            val httpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("consumer_key", serviceType.consumerKey)
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(httpUrl)

            chain.proceed(requestBuilder.build())
        }
    }

    private fun oAuthSignRequest(request: Request, serviceType: OkApiServiceType, account: Account): Request {
        val consumerKey = serviceType.consumerKey
        val consumerSecret = serviceType.consumerSecret
        val accessToken = account.accessToken
        val accessSecret = account.accessSecret

        val nonce = ByteArray(32)
        random.nextBytes(nonce)

        val oauthNonce = ByteString.of(*nonce).base64().replace(Regex("\\W"), "")
        val oauthTimestamp = (System.currentTimeMillis() / 1000).toString()

        val consumerKeyValue = OAuthEncoder.encode(consumerKey)
        val accessTokenValue = OAuthEncoder.encode(accessToken)

        val parameters = TreeMap<String, String>().apply {
            put(OAUTH_CONSUMER_KEY, consumerKeyValue)
            put(OAUTH_ACCESS_TOKEN, accessTokenValue)
            put(OAUTH_NONCE, oauthNonce)
            put(OAUTH_TIMESTAMP, oauthTimestamp)
            put(OAUTH_SIGNATURE_METHOD, OAUTH_SIGNATURE_METHOD_VALUE)
            put(OAUTH_VERSION, OAUTH_VERSION_VALUE)
        }

        val url = request.url()
        for (i in 0 until url.querySize()) {
            parameters.put(OAuthEncoder.encode(url.queryParameterName(i)),
                    OAuthEncoder.encode(url.queryParameterValue(i)))
        }

        val requestBody = request.body()

        if (requestBody != null && requestBody.contentType()?.subtype() == "x-www-form-urlencoded") {
            val body = Buffer()
            requestBody.writeTo(body)

            while (!body.exhausted()) {
                val keyEnd = body.indexOf('='.toByte())
                if (keyEnd == -1L) throw IllegalStateException("Key with no value: " + body.readUtf8())

                val key = body.readUtf8(keyEnd)
                body.skip(1) // Equals.

                val valueEnd = body.indexOf('&'.toByte())
                val value = if (valueEnd == -1L) body.readUtf8() else body.readUtf8(valueEnd)
                if (valueEnd != -1L) body.skip(1) // Ampersand.

                parameters.put(key, value)
            }
        }

        val base = Buffer()
        val method = request.method()
        base.writeUtf8(method)
        base.writeByte(0x26) // '&'
        base.writeUtf8(OAuthEncoder.encode(request.url().newBuilder().query(null).build().toString()))
        base.writeByte(0x26) // '&'

        var first = true
        for ((key, value) in parameters) {
            if (!first) base.writeUtf8(OAuthEncoder.encode("&"))
            first = false
            base.writeUtf8(OAuthEncoder.encode(key))
            base.writeUtf8(OAuthEncoder.encode("="))
            base.writeUtf8(OAuthEncoder.encode(value))
        }

        val signingKey = OAuthEncoder.encode(consumerSecret) + "&" + OAuthEncoder.encode(accessSecret)
        val signature = base.hmacSha1(ByteString.encodeUtf8(signingKey)).base64()

        val authorization = "OAuth " +
                "$OAUTH_CONSUMER_KEY=\"$consumerKeyValue\", " +
                "$OAUTH_NONCE=\"$oauthNonce\", " +
                "$OAUTH_SIGNATURE=\"${OAuthEncoder.encode(signature)}\", " +
                "$OAUTH_SIGNATURE_METHOD=\"$OAUTH_SIGNATURE_METHOD_VALUE\", " +
                "$OAUTH_TIMESTAMP=\"$oauthTimestamp\", " +
                "$OAUTH_ACCESS_TOKEN=\"$accessTokenValue\", " +
                "$OAUTH_VERSION=\"$OAUTH_VERSION_VALUE\""

        return request.newBuilder()
                .addHeader("Authorization", authorization)
                .build()
    }
}