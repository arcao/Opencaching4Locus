package com.arcao.opencaching4locus.ui.dashboard

import android.arch.lifecycle.ViewModel
import com.arcao.opencaching4locus.ui.base.lifecycle.viewmodel.ViewModelKey
import com.arcao.opencaching4locus.ui.livemap.LiveMapModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [LiveMapModule::class])
abstract class DashboardModule {
    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    internal abstract fun bindDashboardViewModel(model: DashboardViewModel): ViewModel
}