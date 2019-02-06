package com.lab.greenpremium.ui.screens.main.profile

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.data.BaseEvent
import com.lab.greenpremium.data.EventsPaginationStateChanging
import com.lab.greenpremium.data.MessageSentEvent
import com.lab.greenpremium.data.ServiceCalculatedEvent
import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.data.entity.Event
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.base.BaseFragment
import com.lab.greenpremium.ui.screens.main.MainActivity
import com.lab.greenpremium.ui.screens.main.contacts.ContactsRecyclerAdapter
import com.lab.greenpremium.utills.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ProfileFragment : BaseFragment(), ProfileContract.View {

    @Inject
    internal lateinit var presenter: ProfilePresenter

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerProfileComponent.builder()
                .appComponent((activity?.application as App).component)
                .profileModule(ProfileModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_profile
    }

    override fun initViews() {
        presenter.onViewCreated()

        button_calc_service.setOnClickListener {
            goToServiceCalculatorScreen()
        }

        button_start_shopping.setOnClickListener {
            (activity as MainActivity).selectMenuItem(R.id.nav_plants)
        }

        setTouchAnimationShrink(button_calc_service)
        setTouchAnimationShrink(button_start_shopping)
    }

    override fun initializeContactsCarousel(contacts: List<Contact>) {
        PagerSnapHelper().attachToRecyclerView(recycler_contacts)
        recycler_contacts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_contacts.adapter = ContactsRecyclerAdapter(contacts, LinearLayoutManager.HORIZONTAL, context?.resources?.getDimension(R.dimen.space_24)?.toInt())
        indicator_contacts.attachToRecyclerView(recycler_contacts)
    }

    override fun initializeServiceCostSection(payment: Double?) {
        val isServiceCalculated = payment != null && payment > 0.0
        button_calc_service.visibility = if (isServiceCalculated) GONE else VISIBLE
        container_cost.visibility = if (isServiceCalculated) VISIBLE else GONE
        if (isServiceCalculated) text_service_price.text = currencyFormat(payment)
    }

    override fun showNoOrdersContainer(show: Boolean) {
        container_no_orders.visibility = if (show) VISIBLE else GONE
    }

    override fun initializeOrdersSection(order_id: Int?, order_supply_date: String?) {
        val isDeliveryExpected = order_id != null && order_supply_date != null
        container_delivery_schedule.visibility = if (isDeliveryExpected) VISIBLE else GONE

        if (isDeliveryExpected) {
            val timestamp = getTimestampFromDateString(order_supply_date, SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()))
            timestamp?.let {
                text_date_day.text = geDayFromTimestamp(timestamp)
                text_date_month.text = getMonthStringFromTimestamp(timestamp)
            }

            delivery.setOnClickListener {
                goToDeliveryScreen(order_id)
            }
        }
    }

    override fun initializeEventsList(events: List<Event>) {
        container_events.visibility = VISIBLE
        recycler_events.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recycler_events.adapter = EventsRecyclerAdapter(events, object : EventsRecyclerAdapter.EventsRecyclerListener {
            override fun onClickPdf(file_path: String) {
                presenter.onClickEventPdf(file_path)
            }

            override fun onRecyclerBottomReached(size: Int) {
                presenter.onEventRecyclerBottomReached(size)
            }
        })

    }

    override fun notifyEventsRecyclerDataChanged() {
        recycler_events.adapter?.notifyDataSetChanged()
    }


    override fun goToServiceCalculatorScreen() {
        (activity as BaseActivity).goToCalcScreen()
    }

    override fun goToDeliveryScreen(order_id: Int?) {
        (activity as BaseActivity).goToDeliveryScreen(order_id)
    }

    override fun openUrlInBrowser(url: String) {
        (activity as BaseActivity).openUrlInBrowser(url)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BaseEvent) {
        when (event::class) {

            ServiceCalculatedEvent::class -> presenter.updateEvents(true)
            MessageSentEvent::class -> presenter.updateEvents(true)

            EventsPaginationStateChanging::class -> {
                presenter.onEventsPaginationStateChanged((event as EventsPaginationStateChanging).enabled)
            }

        }
    }
}