package com.arcao.opencaching4locus.ui.authentication

import android.arch.lifecycle.ViewModel
import com.arcao.opencaching4locus.ui.base.lifecycle.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthenticationModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    internal abstract fun bindAuthorizationViewModel(model: AuthenticationViewModel): ViewModel
}