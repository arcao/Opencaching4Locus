/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arcao.opencaching4locus.ui.base

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatDelegate
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * A [PreferenceActivity] which implements and proxies the necessary calls
 * to be used with AppCompat.
 *
 * This technique can be used with an [Activity] class, not just
 * [PreferenceActivity].
 */
abstract class BasePreferenceActivity : PreferenceActivity(), HasFragmentInjector, HasSupportFragmentInjector {
    private lateinit var delegate: AppCompatDelegate

    @Inject internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject internal lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector
    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> = frameworkFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        delegate = AppCompatDelegate.create(this, null)
        delegate.installViewFactory()
        delegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate.onPostCreate(savedInstanceState)
    }

    val supportActionBar: ActionBar?
        get() = delegate.supportActionBar

    override fun getMenuInflater(): MenuInflater = delegate.menuInflater

    override fun setContentView(@LayoutRes layoutResID: Int) = delegate.setContentView(layoutResID)

    override fun setContentView(view: View) = delegate.setContentView(view)

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) = delegate.setContentView(view, params)

    override fun addContentView(view: View, params: ViewGroup.LayoutParams) = delegate.addContentView(view, params)

    override fun onPostResume() {
        super.onPostResume()
        delegate.onPostResume()
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        delegate.setTitle(title)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        delegate.onConfigurationChanged(newConfig)
    }

    override fun onStop() {
        super.onStop()
        delegate.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy()
    }

    override fun invalidateOptionsMenu() = delegate.invalidateOptionsMenu()
}