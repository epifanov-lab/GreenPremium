package com.lab.greenpremium.ui.screens.main.cart

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.data.CartModel
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.components.item.PlantItemView
import com.lab.greenpremium.ui.screens.base.BaseFragment
import com.lab.greenpremium.ui.screens.main.plants.sub.PlantRecyclerAdapter
import com.lab.greenpremium.utills.currencyFormat
import com.lab.greenpremium.utills.eventbus.BaseEvent
import com.lab.greenpremium.utills.eventbus.CartChangedEvent
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class CartFragment : BaseFragment(), CartContract.View, PlantRecyclerAdapter.OnProductSelectedListener {

    @Inject
    internal lateinit var presenter: CartPresenter

    private lateinit var products: List<Product>

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerCartComponent.builder()
                .appComponent((activity?.application as App).component)
                .cartModule(CartModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_cart
    }

    override fun initViews() {
        products = CartModel.products

        if (products.isNotEmpty()) {
            label_empty_list.visibility = View.GONE
            recycler_plants.visibility = View.VISIBLE
            recycler_plants.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            recycler_plants.adapter = PlantRecyclerAdapter(products, context?.resources?.getDimension(R.dimen.space_24)?.toInt(),
                    this, PlantItemView.PlantViewType.OTHER)
        } else {
            label_empty_list.visibility = View.VISIBLE
            recycler_plants.visibility = View.GONE
        }

        updateTotalCost(0.0)

        //todo подписаться на изменение полей quantity

        setTouchAnimationShrink(button_bill)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: BaseEvent) {
        when (event) {
            is CartChangedEvent -> presenter.onProductCountChanged(event.product)
        }
    }

    override fun updateTotalCost(total: Double) {
        label_total_cost.text = currencyFormat(total)
    }

    override fun onProductSelected(product: Product) {
        //TODO
    }

    override fun onResume() {
        super.onResume()
        recycler_plants.adapter?.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}