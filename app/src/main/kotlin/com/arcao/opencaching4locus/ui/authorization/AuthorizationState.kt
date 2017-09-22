package com.arcao.opencaching4locus.ui.authorization

sealed class AuthorizationState
object AuthorizationStarted : AuthorizationState()
data class AuthorizationRequired(val url: String) : AuthorizationState()
data class AuthorizationError(val throwable: Throwable) : AuthorizationState()
object AuthorizationSuccess : AuthorizationState()
