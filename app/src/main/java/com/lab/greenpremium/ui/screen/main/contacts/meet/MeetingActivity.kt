package com.lab.greenpremium.ui.screen.main.contacts.meet

import android.support.v7.widget.LinearLayoutManager
import com.lab.greenpremium.R
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.ui.screen.main.contacts.ContactsRecyclerAdapter
import com.lab.greenpremium.utills.LogUtil
import com.lab.greenpremium.utills.getMockContactList
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.activity_meeting.*
import java.text.SimpleDateFormat
import java.util.*


class MeetingActivity : BaseActivity() {

    override fun layoutResId(): Int {
        return R.layout.activity_meeting
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        initializeContactsCarousel()
        initializeDateTimePickers()

        button_back.setOnClickListener { finish() }
    }

    private fun initializeContactsCarousel() {
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = ContactsRecyclerAdapter(getMockContactList(), LinearLayoutManager.HORIZONTAL,
                this.resources?.getDimension(R.dimen.space_medium_2)?.toInt())
    }

    private fun initializeDateTimePickers() {
        val calendar = Calendar.getInstance()

        //SimpleDateFormat("WW MMMM", Locale("ru")).format(calendar.time)

        val days: Array<String>

        (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                .forEach {
                    LogUtil.i("Calendar: ${SimpleDateFormat("WW MMMM", Locale("ru")).format(calendar.time)}")
                    calendar.add(Calendar.DATE, 1)
                }

        val hours = (0..23).map { it.toString() }.toTypedArray()
        val minutes = (0..50 step 10).map { it.toString() }.toTypedArray()

        fun initPicker(picker: NumberPicker, data: Array<String>) {
            picker.minValue = 0
            picker.maxValue = data.size - 1
            picker.displayedValues = data
        }

        initPicker(picker_hour, hours)
        initPicker(picker_minute, minutes)
    }
}