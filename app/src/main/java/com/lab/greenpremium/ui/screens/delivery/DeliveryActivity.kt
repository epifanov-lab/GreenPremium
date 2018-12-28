package com.lab.greenpremium.ui.screens.delivery

import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screens.base.BaseActivity
import javax.inject.Inject

class DeliveryActivity : BaseActivity(), DeliveryContract.View {

    @Inject
    internal lateinit var presenter: DeliveryPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_delivery
    }

    override fun initializeDaggerComponent() {
        DaggerDeliveryComponent.builder()
                .appComponent((application as App).component)
                .deliveryModule(DeliveryModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {

    }
}
