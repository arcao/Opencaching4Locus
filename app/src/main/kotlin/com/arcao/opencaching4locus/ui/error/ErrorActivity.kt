package com.arcao.opencaching4locus.ui.error

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.ui.base.BaseActivity

class ErrorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showErrorDialog()
    }

    private fun showErrorDialog() {
        val intent = intent

        AlertDialog.Builder(this).apply {
            setCancelable(false)

            intent.getCharSequenceExtra(EXTRA_TITLE)?.let(this::setTitle)
            intent.getCharSequenceExtra(EXTRA_MESSAGE)?.let(this::setMessage)
            (intent.getCharSequenceExtra(EXTRA_POSITIVE_BUTTON_TEXT) ?: getText(R.string.button_ok)).let {
                setPositiveButton(it) { _, _ ->
                    intent.getParcelableExtra<Intent>(EXTRA_POSITIVE_BUTTON_INTENT)?.let(this@ErrorActivity::startActivity)
                    setResult(RESULT_OK)
                    finish()
                }
            }
            intent.getCharSequenceExtra(EXTRA_NEGATIVE_BUTTON_TEXT)?.let {
                setNegativeButton(it) { _, _ ->
                    intent.getParcelableExtra<Intent>(EXTRA_NEGATIVE_BUTTON_INTENT)?.let(this@ErrorActivity::startActivity)
                    setResult(RESULT_CANCELED)
                    finish()
                }
            }
            intent.getCharSequenceExtra(EXTRA_NEUTRAL_BUTTON_TEXT)?.let {
                setNeutralButton(it) { _, _ ->
                    intent.getParcelableExtra<Intent>(EXTRA_NEUTRAL_BUTTON_INTENT)?.let(this@ErrorActivity::startActivity)
                    setResult(RESULT_FIRST_USER)
                    finish()
                }
            }
        }.show()
    }

    companion object {
        internal const val EXTRA_TITLE = "EXTRA_TITLE"
        internal const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        internal const val EXTRA_POSITIVE_BUTTON_TEXT = "EXTRA_POSITIVE_BUTTON_TEXT"
        internal const val EXTRA_POSITIVE_BUTTON_INTENT = "EXTRA_POSITIVE_BUTTON_INTENT"
        internal const val EXTRA_NEGATIVE_BUTTON_TEXT = "EXTRA_NEGATIVE_BUTTON_TEXT"
        internal const val EXTRA_NEGATIVE_BUTTON_INTENT = "EXTRA_NEGATIVE_BUTTON_INTENT"
        internal const val EXTRA_NEUTRAL_BUTTON_TEXT = "EXTRA_NEUTRAL_BUTTON_TEXT"
        internal const val EXTRA_NEUTRAL_BUTTON_INTENT = "EXTRA_NEUTRAL_BUTTON_INTENT"
    }
}

