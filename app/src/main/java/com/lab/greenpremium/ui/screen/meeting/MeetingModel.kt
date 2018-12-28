package com.lab.greenpremium.ui.screen.meeting

import com.lab.greenpremium.MEETING_MINUTE_STEP
import java.util.*


class MeetingModel(var pickedContactPos: Int = 0,
                   var dayPos: Int = 0,
                   var hour: Int = 12,
                   var minute: Int = 0,
                   var pickedTime: Long = System.currentTimeMillis()) {

    init {
        calculateTime()
    }

    fun calculateTime() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, dayPos)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute * MEETING_MINUTE_STEP)
        pickedTime = calendar.timeInMillis
    }

    override fun toString(): String {
        return "MeetingModel: pickedContactPos = $pickedContactPos, " +
                "dayPos = $dayPos, " +
                "hour = $hour, " +
                "minute = $minute, " +
                "pickedTime = $pickedTime"
    }
}