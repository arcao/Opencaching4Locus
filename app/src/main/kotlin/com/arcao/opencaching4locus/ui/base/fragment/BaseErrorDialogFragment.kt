package com.arcao.opencaching4locus.ui.base.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.ui.base.util.getText

abstract class BaseErrorDialogFragment : BaseDialogFragment() {
    companion object {
        private val PARAM_TITLE = "TITLE"
        private val PARAM_ERROR_MESSAGE = "ERROR_MESSAGE"
        private val PARAM_ADDITIONAL_MESSAGE = "ADDITIONAL_MESSAGE"
    }

    internal fun prepareDialog(@StringRes resTitle: Int, @StringRes resErrorMessage: Int, additionalMessage: String?) {
        arguments = Bundle().apply {
            putInt(PARAM_TITLE, resTitle)
            putInt(PARAM_ERROR_MESSAGE, resErrorMessage)
            putString(PARAM_ADDITIONAL_MESSAGE, additionalMessage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    internal open fun onPositiveButtonClick() =// do nothing
            Unit

    @CallSuper
    internal open fun onDialogBuild(builder: AlertDialog.Builder) {
        builder.setMessage(activity.getText(arguments.getInt(PARAM_ERROR_MESSAGE),
                arguments.getString(PARAM_ADDITIONAL_MESSAGE).orEmpty()))
                .setPositiveButton(R.string.button_ok, {
                    _, _ -> onPositiveButtonClick()
                }
        )

        val title = arguments.getInt(PARAM_TITLE)
        if (title != 0) {
            builder.setTitle(title)
        }
    }

    override final fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val builder = AlertDialog.Builder(activity)
        onDialogBuild(builder)
        return builder.create()
    }
}
