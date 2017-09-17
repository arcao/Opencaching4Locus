package com.arcao.opencaching4locus

import com.arcao.opencaching4locus.ui.authorization.AuthorizationActivity
import com.arcao.opencaching4locus.ui.dashboard.DashboardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class AndroidBindingModule {
    @ContributesAndroidInjector
    abstract fun dashboardActivity(): DashboardActivity

    @ContributesAndroidInjector
    abstract fun authorizationActivity(): AuthorizationActivity
}