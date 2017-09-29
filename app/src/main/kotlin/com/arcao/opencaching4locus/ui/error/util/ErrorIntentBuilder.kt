package com.arcao.opencaching4locus.ui.error.util

import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import com.arcao.opencaching4locus.ui.error.ErrorActivity

class ErrorIntentBuilder(private val context: Context) {
    private val intent = Intent(context, ErrorActivity::class.java)

    fun build(): Intent = Intent(intent)

    fun title(title: CharSequence): ErrorIntentBuilder {
        intent.putExtra(ErrorActivity.EXTRA_TITLE, title)
        return this
    }

    fun title(@StringRes title: Int): ErrorIntentBuilder {
        message(context.getText(title))
        return this
    }

    fun message(message: CharSequence): ErrorIntentBuilder {
        intent.putExtra(ErrorActivity.EXTRA_MESSAGE, message)
        return this
    }

    fun message(@StringRes message: Int): ErrorIntentBuilder {
        message(context.getText(message))
        return this
    }

    fun positiveButton(message: CharSequence, positiveIntent: Intent? = null): ErrorIntentBuilder {
        intent.putExtra(ErrorActivity.EXTRA_POSITIVE_BUTTON_TEXT, message)
        intent.putExtra(ErrorActivity.EXTRA_POSITIVE_BUTTON_INTENT, positiveIntent)
        return this
    }

    fun positiveButton(@StringRes message: Int, positiveIntent: Intent? = null): ErrorIntentBuilder {
        positiveButton(context.getText(message), positiveIntent)
        return this
    }


    fun negativeButton(message: CharSequence, negativeIntent: Intent? = null): ErrorIntentBuilder {
        intent.putExtra(ErrorActivity.EXTRA_NEGATIVE_BUTTON_TEXT, message)
        intent.putExtra(ErrorActivity.EXTRA_NEGATIVE_BUTTON_INTENT, negativeIntent)
        return this
    }

    fun negativeButton(@StringRes message: Int, negativeIntent: Intent? = null): ErrorIntentBuilder {
        negativeButton(context.getText(message), negativeIntent)
        return this
    }

    fun neutralButton(message: CharSequence, neutralIntent: Intent? = null): ErrorIntentBuilder {
        intent.putExtra(ErrorActivity.EXTRA_NEUTRAL_BUTTON_TEXT, message)
        intent.putExtra(ErrorActivity.EXTRA_NEUTRAL_BUTTON_INTENT, neutralIntent)
        return this
    }

    fun neutralButton(@StringRes message: Int, neutralIntent: Intent? = null): ErrorIntentBuilder {
        neutralButton(context.getText(message), neutralIntent)
        return this
    }
}