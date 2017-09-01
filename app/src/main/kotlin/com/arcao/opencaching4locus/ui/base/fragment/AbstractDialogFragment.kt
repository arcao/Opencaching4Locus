package com.arcao.opencaching4locus.ui.base.fragment

import android.app.DialogFragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle

abstract class AbstractDialogFragment : DialogFragment() {
    companion object {
        private val PARAM_DISMISS_LATER = "DISMISS_LATER"
    }

    init {
        try {
            if (arguments == null)
                arguments = Bundle()
        } catch (e: Exception) {
            // do nothing
        }

    }

    // This is work around for the situation when method show is called after saving
    // state even if you do all right. Especially when show is called after click on
    // a button.
    override fun show(transaction: FragmentTransaction, tag: String): Int {
        try {
            return super.show(transaction, tag)
        } catch (e: IllegalStateException) {
            // ignore
            return 0
        }

    }

    override fun show(manager: FragmentManager, tag: String) {
        try {
            super.show(manager, tag)
        } catch (e: IllegalStateException) {
            // ignore
        }

    }

    // This is to work around what is apparently a bug. If you don't have it
    // here the dialog will be dismissed on rotation, so tell it not to dismiss.
    override fun onDestroyView() {
        if (dialog != null && retainInstance)
            dialog.setDismissMessage(null)

        super.onDestroyView()
    }


    override fun dismiss() {
        // this fix IllegalStateException when App is hidden
        if (!isAdded || fragmentManager == null) {
            if (arguments != null)
                arguments.putBoolean(PARAM_DISMISS_LATER, true)

            return
        }

        try {
            super.dismiss()
        } catch (e: IllegalStateException) {
            dismissAllowingStateLoss()
        }

    }

    override fun onResume() {
        super.onResume()

        if (arguments != null && arguments.getBoolean(PARAM_DISMISS_LATER, false)) {
            dismiss()
        }
    }

    fun isShowing(): Boolean = dialog != null && dialog.isShowing
}
