package com.lab.greenpremium.ui.screens.main.plants.sub

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.BaseEvent
import com.lab.greenpremium.data.ProductPaginationStateChanging
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.base.BaseFragment
import kotlinx.android.synthetic.main.sub_fragment_plants.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class PlantsSubFragment : BaseFragment(), PlantsSubContract.View {

    @Inject
    internal lateinit var presenter: PlantsSubPresenter

    companion object {
        fun newInstance(sectionPosition: Int): PlantsSubFragment {
            val fragment = PlantsSubFragment()
            val args = Bundle()
            args.putInt(KEY_OBJECT, sectionPosition)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initializeDaggerComponent() {
        DaggerPlantsSubComponent.builder()
                .appComponent((activity?.application as App).component)
                .plantsSubModule(PlantsSubModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_plants
    }

    override fun initViews() {
        val sectionPosition = arguments!!.getInt(KEY_OBJECT)
        presenter.onViewCreated(sectionPosition)
    }

    override fun initializeCatalog(products: List<Product>, isDemo: Boolean) {
        recycler_plants.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recycler_plants.adapter = PlantRecyclerAdapter(products, context?.resources?.getDimension(R.dimen.space_24)?.toInt(), isDemo = isDemo,
                listener = object : PlantRecyclerAdapter.PlantsRecyclerListener {
                    override fun onProductSelected(product: Product) {
                        if (!isDemo) presenter.onProductSelected(product)
                    }

                    override fun onRecyclerBottomReached(size: Int) {
                        presenter.onProductsRecyclerBottomReached(size)
                    }
                })
    }

    override fun notifyRecyclerDataChanged() {
        recycler_plants.adapter?.notifyDataSetChanged()
    }

    override fun goToDetails(product: Product) {
        (activity as BaseActivity).goToPlantDetailActivity(product)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BaseEvent) {
        when (event::class) {
            ProductPaginationStateChanging::class -> {
                presenter.onProductPaginationStateChanged((event as ProductPaginationStateChanging).enabled)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        recycler_plants.adapter?.notifyDataSetChanged()
    }
}