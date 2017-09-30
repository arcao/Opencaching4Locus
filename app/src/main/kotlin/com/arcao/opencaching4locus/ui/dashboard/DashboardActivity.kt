package com.arcao.opencaching4locus.ui.dashboard

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.arcao.opencaching4locus.R
import com.arcao.opencaching4locus.databinding.ActivityDashboardBinding
import com.arcao.opencaching4locus.ui.base.BaseActivity
import javax.inject.Inject

class DashboardActivity : BaseActivity() {
    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
        viewModel.intent = intent
        viewModel.startActivityLiveData.observe(this, Observer {
            startActivityForResult(it, 0)
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.viewModel = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            finish()
        }
    }
}
