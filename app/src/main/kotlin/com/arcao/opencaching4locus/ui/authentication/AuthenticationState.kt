package com.arcao.opencaching4locus.ui.authentication

sealed class AuthenticationState
object AuthenticationStarted : AuthenticationState()
data class AuthenticationRequired(val url: String) : AuthenticationState()
data class AuthenticationError(val throwable: Throwable) : AuthenticationState()
object AuthenticationSuccess : AuthenticationState()
