package com.arcao.opencaching4locus.ui.base.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.ui.base.util.ResourcesUtil

class AbstractErrorDialogFragment : AbstractDialogFragment() {
    companion object {
        private val PARAM_TITLE = "TITLE"
        private val PARAM_ERROR_MESSAGE = "ERROR_MESSAGE"
        private val PARAM_ADDITIONAL_MESSAGE = "ADDITIONAL_MESSAGE"
    }

    protected fun prepareDialog(@StringRes resTitle: Int, @StringRes resErrorMessage: Int, additionalMessage: String?) {
        val args = Bundle()
        args.putInt(PARAM_TITLE, resTitle)
        args.putInt(PARAM_ERROR_MESSAGE, resErrorMessage)
        args.putString(PARAM_ADDITIONAL_MESSAGE, additionalMessage)
        arguments = args
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    protected fun onPositiveButtonClick() {
        // do nothing
    }

    protected fun onDialogBuild(builder: AlertDialog.Builder) {
        val args = arguments

        builder.setMessage(ResourcesUtil.getText(activity, args.getInt(PARAM_ERROR_MESSAGE),
                args.getString(PARAM_ADDITIONAL_MESSAGE).orEmpty()))
                .setPositiveButton(R.string.button_ok, {
                    _, _ -> onPositiveButtonClick()
                }
        )

        val title = args.getInt(PARAM_TITLE)
        if (title != 0) {
            builder.setTitle(title)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val builder = AlertDialog.Builder(activity)
        onDialogBuild(builder)
        return builder.create()
    }
}
