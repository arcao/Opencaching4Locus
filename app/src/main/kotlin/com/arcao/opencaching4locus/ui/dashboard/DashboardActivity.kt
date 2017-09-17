package com.arcao.opencaching4locus.ui.dashboard

import android.os.Bundle
import android.widget.Toast
import com.arcao.opencaching4locus.App
import com.arcao.opencaching4locus.ui.base.BaseActivity
import javax.inject.Inject

class DashboardActivity : BaseActivity() {
    @Inject lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(app, "test", Toast.LENGTH_LONG).show()
        finish()
    }
}
