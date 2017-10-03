package com.arcao.opencaching4locus.ui.settings

import com.arcao.opencaching4locus.ui.settings.fragment.AccountsPreferenceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsModule {
    @ContributesAndroidInjector
    abstract fun accountsPreferenceFragment(): AccountsPreferenceFragment

}