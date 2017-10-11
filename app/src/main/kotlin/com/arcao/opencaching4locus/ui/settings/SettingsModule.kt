package com.arcao.opencaching4locus.ui.settings

import com.arcao.opencaching4locus.ui.settings.fragment.AccountsPreferenceFragment
import com.arcao.opencaching4locus.ui.settings.fragment.LiveMapPreferenceFragment
import com.arcao.opencaching4locus.ui.settings.fragment.LiveMapAccountsPreferenceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsModule {
    @ContributesAndroidInjector
    abstract fun accountsPreferenceFragment(): AccountsPreferenceFragment

    @ContributesAndroidInjector
    abstract fun liveMapPreferenceFragment(): LiveMapPreferenceFragment

    @ContributesAndroidInjector
    abstract fun liveMapServicesPreferenceFragment(): LiveMapAccountsPreferenceFragment

}