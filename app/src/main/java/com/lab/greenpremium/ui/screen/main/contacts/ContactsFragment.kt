package com.lab.greenpremium.ui.screen.main.contacts

import android.support.v7.widget.LinearLayoutManager
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.base.BaseFragment
import com.lab.greenpremium.utills.getMockContactList
import kotlinx.android.synthetic.main.fragment_profile.*


class ContactsFragment : BaseFragment() {

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun initializeDaggerComponent() {
        //TODO impl
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_contacts
    }

    override fun initViews() {
        initializeContactsRecyclerView()
        updateTimeLabels(System.currentTimeMillis())
    }

    private fun initializeContactsRecyclerView() {
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = ContactsRecyclerAdapter(getMockContactList(), LinearLayoutManager.VERTICAL,
                context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
    }

    private fun updateTimeLabels(time: Long) {

    }
}