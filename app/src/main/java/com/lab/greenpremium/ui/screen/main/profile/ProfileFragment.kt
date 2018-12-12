package com.lab.greenpremium.ui.screen.main.profile

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.data.entity.Event
import com.lab.greenpremium.ui.components.ScrollLayoutManager
import com.lab.greenpremium.ui.components.adapters.ContactsRecyclerAdapter
import com.lab.greenpremium.ui.components.adapters.EventsRecyclerAdapter
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.utills.OnAnimationEndListener
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.fragment_profile.*
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

        button_start_shopping.setOnClickListener {
            container_no_events.visibility = GONE
            container_events.visibility = VISIBLE
        }

        setTouchAnimationShrink(button_calc_service, object : OnAnimationEndListener {
            override fun onAnimationEndEvent() {
                button_calc_service.visibility = GONE
                container_cost.visibility = VISIBLE
            }
        })

        setTouchAnimationShrink(button_start_shopping)
    }

    override fun showLoadingStub(show: Boolean) {
        container_main.visibility = if (show) View.VISIBLE else View.INVISIBLE
        progress.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }

    override fun initializeContactsCarousel(contacts: List<Contact>) {
        PagerSnapHelper().attachToRecyclerView(recycler_contacts)
        recycler_contacts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_contacts.adapter = ContactsRecyclerAdapter(contacts, LinearLayoutManager.HORIZONTAL, context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
        indicator_contacts.attachToRecyclerView(recycler_contacts)
    }

    override fun initializeEventsList(events: List<Event>) {
        button_calc_service.visibility = GONE
        container_cost.visibility = VISIBLE

        recycler_events.layoutManager = ScrollLayoutManager(context).also { it.setScrollEnabled(false) }
        recycler_events.adapter = EventsRecyclerAdapter(events)
    }
}