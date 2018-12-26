package com.lab.greenpremium.ui.screen.calculator

import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import javax.inject.Inject

class CalcActivity : BaseActivity(), CalcContract.View {

    @Inject
    internal lateinit var presenter: CalcPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_calculator
    }

    override fun initializeDaggerComponent() {
        DaggerCalcComponent.builder()
                .appComponent((application as App).component)
                .calcModule(CalcModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {

    }
}
