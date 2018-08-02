package com.lab.greenpremium.ui.screen.main.contacts.meet

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import com.lab.greenpremium.MEETING_MINUTE_STEP
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.ui.screen.main.contacts.ContactsRecyclerAdapter
import com.lab.greenpremium.utills.LogUtil
import com.lab.greenpremium.utills.getMockContactList
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.activity_meeting.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MeetingActivity : BaseActivity() {

    private lateinit var model: MeetingModel

    override fun layoutResId(): Int {
        return R.layout.activity_meeting
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        model = MeetingModel(getMockContactList())

        initializeContactsCarousel()
        initializeDateTimePickers()

        button_proceed.setOnClickListener {
            showSnackbar("Встреча с ${model.contactList[model.pickedContactPos].name} назначена на" +
                    " ${SimpleDateFormat("dd MMMM hh : mm", Locale("ru")).format(model.pickedTime)}")
        }

        button_back.setOnClickListener { finish() }
    }

    private fun initializeContactsCarousel() {
        LinearSnapHelper().attachToRecyclerView(recycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = ContactsRecyclerAdapter(model.contactList, LinearLayoutManager.HORIZONTAL, this.resources?.getDimension(R.dimen.space_medium_2)?.toInt())

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    model.pickedContactPos = (recycler.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
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