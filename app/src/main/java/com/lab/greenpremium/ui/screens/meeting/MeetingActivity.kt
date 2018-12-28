package com.lab.greenpremium.ui.screens.meeting

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import com.lab.greenpremium.App
import com.lab.greenpremium.MEETING_MINUTE_STEP
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.main.contacts.ContactsRecyclerAdapter
import com.lab.greenpremium.utills.setTouchAnimationShrink
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.activity_meeting.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MeetingActivity : BaseActivity(), MeetingContract.View {

    @Inject
    internal lateinit var presenter: MeetingPresenter

    private lateinit var model: MeetingModel

    override fun layoutResId(): Int {
        return R.layout.activity_meeting
    }

    override fun initializeDaggerComponent() {
        DaggerMeetingComponent.builder()
                .appComponent((application as App).component)
                .meetingModule(MeetingModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        presenter.onViewCreated()

        model = MeetingModel()

        initializeDateTimePickers()

        button_proceed.setOnClickListener { presenter.addMeeting(model) }

        button_back.setOnClickListener { finish() }

        setTouchAnimationShrink(button_proceed)
    }

    override fun initializeContactsCarousel(contacts: List<Contact>) {
        PagerSnapHelper().attachToRecyclerView(recycler_contacts)

        recycler_contacts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_contacts.adapter = ContactsRecyclerAdapter(contacts, LinearLayoutManager.HORIZONTAL, this.resources?.getDimension(R.dimen.space_24)?.toInt())
        indicator_contacts.attachToRecyclerView(recycler_contacts)

        recycler_contacts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    model.pickedContactPos = (recycler_contacts.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                }
            }
        })
    }

    private fun initializeDateTimePickers() {
        fun initPicker(picker: NumberPicker, data: Array<String>) {
            picker.minValue = 0
            picker.maxValue = data.size - 1
            picker.displayedValues = data

            when (picker.id) {
                R.id.picker_day -> picker.value = model.dayPos
                R.id.picker_hour -> picker.value = model.hour
                R.id.picker_minute -> picker.value = model.minute
            }

            picker.setOnValueChangedListener { _, _, newVal ->
                when (picker.id) {
                    R.id.picker_day -> model.dayPos = newVal
                    R.id.picker_hour -> model.hour = newVal
                    R.id.picker_minute -> model.minute = newVal
                }

                model.calculateTime()
            }
        }

        val calendar = Calendar.getInstance()
        val days: ArrayList<String> = ArrayList()
        (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                .forEach {
                    days.add(if (it == 1) getString(R.string.today) else SimpleDateFormat("dd MMMM", Locale("ru")).format(calendar.time))
                    calendar.add(Calendar.DATE, 1)
                }

        val hours = (0..23).map { String.format("%02d", it) }.toTypedArray()
        val minutes = (0..50 step MEETING_MINUTE_STEP).map { String.format("%02d", it) }.toTypedArray()

        initPicker(picker_day, days.toTypedArray())
        initPicker(picker_hour, hours)
        initPicker(picker_minute, minutes)
    }
}