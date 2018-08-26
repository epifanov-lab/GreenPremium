package com.lab.greenpremium.ui.screen.main.profile

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View.GONE
import android.view.View.VISIBLE
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.customview.CustomGridLayoutManager
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.ui.screen.main.contacts.ContactsRecyclerAdapter
import com.lab.greenpremium.utills.getMockContactList
import com.lab.greenpremium.utills.getMockEventsList
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_profile
    }

    override fun initViews() {
        initializeContactsCarousel()
        initializeEventsList()

        button_calc_service.setOnClickListener {
            button_calc_service.visibility = GONE
            container_cost.visibility = VISIBLE
        }

        button_start_shopping.setOnClickListener {
            container_no_events.visibility = GONE
            container_events.visibility = VISIBLE
        }
    }

    private fun initializeContactsCarousel() {
        PagerSnapHelper().attachToRecyclerView(recycler_contacts)

        recycler_contacts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_contacts.adapter = ContactsRecyclerAdapter(getMockContactList(), LinearLayoutManager.HORIZONTAL, context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
        indicator_contacts.attachToRecyclerView(recycler_contacts)
    }

    private fun initializeEventsList() {
        recycler_events.layoutManager = CustomGridLayoutManager(context).also { it.setScrollEnabled(false) }
        recycler_events.adapter = EventsRecyclerAdapter(getMockEventsList())
    }
}