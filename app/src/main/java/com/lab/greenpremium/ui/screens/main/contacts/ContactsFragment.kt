package com.lab.greenpremium.ui.screens.main.contacts

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_RESULT_ADD_MEETING
import com.lab.greenpremium.R
import com.lab.greenpremium.data.MeetingAddedEvent
import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.ui.screens.base.BaseFragment
import com.lab.greenpremium.ui.screens.meeting.MeetingActivity
import com.lab.greenpremium.utills.geDayFromTimestamp
import com.lab.greenpremium.utills.getMonthStringFromTimestamp
import com.lab.greenpremium.utills.getTimeFromTimestamp
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
        presenter.onViewCreated()

        button_schedule_meet.setOnClickListener { activity?.startActivityForResult(Intent(context, MeetingActivity::class.java), KEY_RESULT_ADD_MEETING) }

        setTouchAnimationShrink(button_schedule_meet)
    }

    override fun initializeContactsCarousel(contacts: List<Contact>) {
        recycler_contacts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_contacts.adapter = ContactsRecyclerAdapter(contacts, LinearLayoutManager.VERTICAL, context?.resources?.getDimension(R.dimen.space_24)?.toInt())
    }

    /* Информация по встрече спрятана по просьбе Юлии */
    override fun updateNextMeetingLabels(timestamp: Long?) {
        var tempTimestamp = timestamp

        tempTimestamp = null // TODO DELETE FOR CORRECT WORK

        text_time?.visibility = if (tempTimestamp != null) VISIBLE else INVISIBLE
        container_date?.visibility = if (tempTimestamp != null) VISIBLE else INVISIBLE
        text_message?.text = getString(if (tempTimestamp != null) R.string.contacts_message_employee_meeting_when_set else R.string.contacts_message_employee_meeting)

        tempTimestamp?.let {
            text_time.text = getTimeFromTimestamp(tempTimestamp)
            text_date_day.text = geDayFromTimestamp(tempTimestamp)
            text_date_month.text = getMonthStringFromTimestamp(tempTimestamp)
        }
    }

    /* по просьбе Юлии: кнопка всегда доступна (кроме демо) */
    override fun setButtonScheduleEnabled(enabled: Boolean) {
        button_schedule_meet.isEnabled = enabled
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
    fun onEvent(event: MeetingAddedEvent) {
        presenter.updateMeetingList()
    }
}