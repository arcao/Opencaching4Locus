package com.arcao.opencaching4locus.ui.dashboard.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ToggleButton
import com.arcao.opencaching4locus.R

class DashboardButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.attr.dashboardButtonStyle,
        defStyleRes: Int = R.style.Widget_AppTheme_DashboardButton
) : ToggleButton(context, attrs, defStyleAttr, defStyleRes) {
    private val mToggleable: Boolean

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DashboardButton, defStyleAttr, defStyleRes)
        mToggleable = a.getBoolean(R.styleable.DashboardButton_toggleable, false)
        a.recycle()
    }

    override fun toggle() {
        if (mToggleable) {
            super.toggle()
        }
    }

    override fun setChecked(checked: Boolean) {
        if (!mToggleable)
            return

        super.setChecked(checked)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        alpha = if (enabled) ALPHA_ENABLED else ALPHA_DISABLED
    }

    companion object {
        private val ALPHA_DISABLED = 0.38f
        private val ALPHA_ENABLED = 1f
    }
}
