package com.arcao.opencaching4locus.ui.dashboard

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.arcao.opencaching4locus.App
import com.arcao.opencaching4locus.ui.base.BaseActivity
import dagger.Binds
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Inject

class DashboardActivity : BaseActivity() {
    @Inject lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(app, "test", Toast.LENGTH_LONG).show()
        finish()
    }

    // =============== Dagger 2 ===============
    @dagger.Subcomponent
    internal interface Component : AndroidInjector<DashboardActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<DashboardActivity>()
    }

    @dagger.Module(subcomponents = arrayOf(Component::class))
    internal abstract inner class Module {
        @Binds
        @IntoMap
        @ActivityKey(DashboardActivity::class)
        internal abstract fun bind(builder: Component.Builder): AndroidInjector.Factory<out Activity>
    }
}
