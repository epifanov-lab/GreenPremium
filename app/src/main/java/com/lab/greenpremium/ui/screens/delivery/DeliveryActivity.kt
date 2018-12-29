package com.lab.greenpremium.ui.screens.delivery

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.NO_DATA
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.main.plants.sub.PlantRecyclerAdapter
import com.lab.greenpremium.utills.LogUtil
import kotlinx.android.synthetic.main.activity_delivery.*
import javax.inject.Inject

class DeliveryActivity : BaseActivity(), DeliveryContract.View, PlantRecyclerAdapter.OnProductSelectedListener {

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
        presenter.onViewCreated(intent.getIntExtra(KEY_OBJECT, NO_DATA))

        button_back.setOnClickListener { onBackPressed() }
    }

    override fun initializeProductsList(products: List<Product>) {
        LogUtil.e(products.toString())
        recycler_plants.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        recycler_plants.adapter = PlantRecyclerAdapter(products, applicationContext?.resources?.getDimension(R.dimen.space_24)?.toInt(),
                this, true)
    }

    override fun onProductSelected(product: Product) {
        // ignore
    }
}
