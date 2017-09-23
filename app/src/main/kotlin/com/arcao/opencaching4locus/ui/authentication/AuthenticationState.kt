package com.arcao.opencaching4locus.ui.authentication

sealed class AuthenticationState
object AuthenticationStarted : AuthenticationState()
data class RequestTokenReceived(val url: String) : AuthenticationState()
object AccessTokenRequestSent : AuthenticationState()
data class AuthenticationError(val throwable: Throwable) : AuthenticationState()
object AuthenticationSuccess : AuthenticationState()
