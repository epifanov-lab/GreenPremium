package com.lab.greenpremium.ui.screen.main.contacts

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.lab.greenpremium.App
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.ui.screen.main.contacts.meet.MeetingActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.fragment_contacts.*
import javax.inject.Inject


class ContactsFragment : BaseFragment(), ContactsContract.View {

    @Inject
    internal lateinit var presenter: ContactsPresenter

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun initializeDaggerComponent() {
        DaggerContactsComponent.builder()
                .appComponent((activity?.application as App).component)
                .contactsModule(ContactsModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.fragment_contacts
    }

    override fun initViews() {
        initializeContactsRecyclerView()
        updateTimeLabels(System.currentTimeMillis())

        button_schedule_meet.setOnClickListener { startActivity(Intent(context, MeetingActivity::class.java)) }
        setTouchAnimationShrink(button_schedule_meet)
    }

    private fun initializeContactsRecyclerView() {
        recycler_contacts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //recycler_contacts.adapter = ContactsRecyclerAdapter(getMockContactList(), LinearLayoutManager.VERTICAL, context?.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
    }

    private fun updateTimeLabels(time: Long) {

    }
}