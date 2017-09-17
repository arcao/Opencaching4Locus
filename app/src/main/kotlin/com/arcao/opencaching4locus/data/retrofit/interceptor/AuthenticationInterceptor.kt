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
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class AuthenticationInterceptor(private val account: Account, private val serviceType: OkApiServiceType) : Interceptor {
    private val OAUTH_CONSUMER_KEY = "oauth_consumer_key"
    private val OAUTH_NONCE = "oauth_nonce"
    private val OAUTH_SIGNATURE = "oauth_signature"
    private val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
    private val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
    private val OAUTH_TIMESTAMP = "oauth_timestamp"
    private val OAUTH_ACCESS_TOKEN = "oauth_token"
    private val OAUTH_VERSION = "oauth_version"
    private val OAUTH_VERSION_VALUE = "1.0"

    private val random = SecureRandom()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        return if (account.authenticated()) {
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
        val accessToken = account.accessToken()
        val accessSecret = account.accessSecret()

        val nonce = ByteArray(32)
        random.nextBytes(nonce)

        val oauthNonce = ByteString.of(*nonce).base64().replace("\\W", "")
        val oauthTimestamp = System.currentTimeMillis().toString()

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

        val keySpec = SecretKeySpec(signingKey.toByteArray(), "HmacSHA1")
        val mac: Mac
        try {
            mac = Mac.getInstance("HmacSHA1")
            mac.init(keySpec)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException(e)
        } catch (e: InvalidKeyException) {
            throw IllegalStateException(e)
        }

        val result = mac.doFinal(base.readByteArray())
        val signature = ByteString.of(*result).base64()

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