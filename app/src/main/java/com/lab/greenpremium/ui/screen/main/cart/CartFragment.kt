package com.lab.greenpremium.ui.screen.main.cart

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.data.repository.user.UserRepository
import com.lab.greenpremium.ui.components.adapters.PlantRecyclerAdapter
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.utills.currencyFormat
import com.lab.greenpremium.utills.eventbus.BaseEvent
import com.lab.greenpremium.utills.eventbus.PlantCountChangedEvent
import kotlinx.android.synthetic.main.fragment_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class CartFragment : BaseFragment() {

    private lateinit var plants: List<Plant>

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_cart
    }

    override fun initViews() {
        plants = UserRepository.plants.filter { it.count > 0 }

        recycler_plants.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recycler_plants.adapter = PlantRecyclerAdapter(plants,
                context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt(), this)

        updateTotalCost(plants)

        //todo подписаться на изменение полей count
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: BaseEvent) {
        when (event) {
            is PlantCountChangedEvent -> updateTotalCost(plants)
        }
    }

    private fun updateTotalCost(plants: List<Plant>) {
        var total = 0.0
        plants.forEach { total += it.price * it.count }
        label_total_cost.text = currencyFormat(total)
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