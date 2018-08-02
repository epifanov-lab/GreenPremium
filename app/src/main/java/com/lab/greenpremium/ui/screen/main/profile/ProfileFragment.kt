package com.lab.greenpremium.ui.screen.main.profile

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.ui.screen.main.contacts.ContactsRecyclerAdapter
import com.lab.greenpremium.utills.getMockContactList
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
    }

    private fun initializeContactsCarousel() {
        LinearSnapHelper().attachToRecyclerView(recycler)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = ContactsRecyclerAdapter(getMockContactList(), LinearLayoutManager.HORIZONTAL,
                context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
    }

}