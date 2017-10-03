package com.arcao.opencaching4locus

import android.arch.lifecycle.ViewModelProvider
import com.arcao.opencaching4locus.ui.authentication.AuthenticationActivity
import com.arcao.opencaching4locus.ui.authentication.AuthenticationModule
import com.arcao.opencaching4locus.ui.base.lifecycle.viewmodel.AppViewModelFactory
import com.arcao.opencaching4locus.ui.dashboard.DashboardActivity
import com.arcao.opencaching4locus.ui.dashboard.DashboardModule
import com.arcao.opencaching4locus.ui.livemap.LiveMapJobService
import com.arcao.opencaching4locus.ui.livemap.LiveMapModule
import com.arcao.opencaching4locus.ui.settings.SettingsActivity
import com.arcao.opencaching4locus.ui.settings.SettingsModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector





@Module
abstract class AndroidBindingModule {
    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = arrayOf(DashboardModule::class))
    abstract fun dashboardActivity(): DashboardActivity

    @ContributesAndroidInjector(modules = arrayOf(SettingsModule::class))
    abstract fun settingsActivity(): SettingsActivity

    @ContributesAndroidInjector(modules = arrayOf(AuthenticationModule::class))
    abstract fun authenticationActivity(): AuthenticationActivity

    @ContributesAndroidInjector(modules = arrayOf(LiveMapModule::class))
    abstract fun liveMapJobService(): LiveMapJobService
}