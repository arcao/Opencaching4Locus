package com.arcao.opencaching4locus.ui.authorization

import android.arch.lifecycle.ViewModel
import com.arcao.opencaching4locus.ui.base.lifecycle.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthorizationModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    internal abstract fun bindAuthorizationViewModel(model: AuthorizationViewModel): ViewModel
}