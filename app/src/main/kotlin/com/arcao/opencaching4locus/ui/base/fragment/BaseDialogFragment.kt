package com.arcao.opencaching4locus.ui.base.fragment

import android.app.*
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment(), HasFragmentInjector {
    @Inject internal open lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        AndroidInjection.inject(this)
        super.onAttach(activity)
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    // This is work around for the situation when method show is called after saving
    // state even if you do all right. Especially when show is called after click on
    // a button.
    override fun show(transaction: FragmentTransaction, tag: String): Int = try {
        super.show(transaction, tag)
    } catch (e: IllegalStateException) {
        // ignore
        0
    }

    override fun show(manager: FragmentManager, tag: String) = try {
        super.show(manager, tag)
    } catch (e: IllegalStateException) {
        // ignore
    }

    // This is to work around what is apparently a bug. If you don't have it
    // here the dialog will be dismissed on rotation, so tell it not to dismiss.
    override fun onDestroyView() {
        if (dialog != null && retainInstance)
            dialog.setDismissMessage(null)

        super.onDestroyView()
    }


    override fun dismiss() = try {
        super.dismiss()
    } catch (e: IllegalStateException) {
        dismissAllowingStateLoss()
    }

    internal fun isShowing(): Boolean = dialog != null && dialog.isShowing
}
