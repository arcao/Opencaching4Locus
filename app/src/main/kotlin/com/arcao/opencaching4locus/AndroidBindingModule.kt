package com.arcao.opencaching4locus

import android.arch.lifecycle.ViewModelProvider
import com.arcao.opencaching4locus.ui.authentication.AuthenticationActivity
import com.arcao.opencaching4locus.ui.authentication.AuthenticationModule
import com.arcao.opencaching4locus.ui.base.lifecycle.viewmodel.AppViewModelFactory
import com.arcao.opencaching4locus.ui.dashboard.DashboardActivity
import com.arcao.opencaching4locus.ui.livemap.LiveMapJobService
import com.arcao.opencaching4locus.ui.livemap.LiveMapModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector





@Module
abstract class AndroidBindingModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    internal abstract fun dashboardActivity(): DashboardActivity

    @ContributesAndroidInjector(modules = arrayOf(AuthenticationModule::class))
    internal abstract fun authenticationActivity(): AuthenticationActivity

    @ContributesAndroidInjector(modules = arrayOf(LiveMapModule::class))
    internal abstract fun liveMapJobService(): LiveMapJobService
}