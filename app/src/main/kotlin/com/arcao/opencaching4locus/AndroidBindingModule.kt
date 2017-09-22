package com.arcao.opencaching4locus

import android.arch.lifecycle.ViewModelProvider
import com.arcao.opencaching4locus.ui.authorization.AuthorizationActivity
import com.arcao.opencaching4locus.ui.authorization.AuthorizationModule
import com.arcao.opencaching4locus.ui.base.lifecycle.viewmodel.AppViewModelFactory
import com.arcao.opencaching4locus.ui.dashboard.DashboardActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector





@Module
abstract class AndroidBindingModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    internal abstract fun dashboardActivity(): DashboardActivity

    @ContributesAndroidInjector(modules = arrayOf(AuthorizationModule::class))
    internal abstract fun authorizationActivity(): AuthorizationActivity
}