package com.lab.greenpremium.ui.screen.main.profile

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import com.lab.greenpremium.R
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
    }

    private fun initializeContactsCarousel() {
        LinearSnapHelper().attachToRecyclerView(recycler_contacts)
        recycler_contacts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_contacts.adapter = ContactsRecyclerAdapter(getMockContactList(), LinearLayoutManager.HORIZONTAL,
                context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
    }

    private fun initializeEventsList() {
        recycler_events.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_events.adapter = EventsRecyclerAdapter(getMockEventsList())
    }
}