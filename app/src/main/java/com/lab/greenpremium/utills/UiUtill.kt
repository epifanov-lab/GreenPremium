package com.lab.greenpremium.utills

import android.graphics.*
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.utills.eventbus.PlantCountChangedEvent
import org.greenrobot.eventbus.EventBus


fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
    val output = Bitmap.createBitmap(bitmap.width, bitmap
            .height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val color = -0xbdbdbe
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val rectF = RectF(rect)
    val roundPx = pixels.toFloat()

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)

    return output
}

class PlantItemCountControlsHelper(val plant: Plant,
                                   val counter: TextView,
                                   val add: View,
                                   val remove: View) {


    val repeatUpdateHandler = Handler()
    var isIncrementing = false
    var isDecrementing = false

    init {

        add.run {
            setOnClickListener { setCounter(++plant.count) }
            setOnLongClickListener {
                isIncrementing = true
                repeatUpdateHandler.post(RptUpdater())
                false
            }

            setOnTouchListener { v, event ->
                if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) && isIncrementing) {
                    isIncrementing = false
                }
                false
            }
        }

        remove.run {
            setOnClickListener { setCounter(--plant.count) }
            setOnLongClickListener {
                isDecrementing = true
                repeatUpdateHandler.post(RptUpdater())
                false
            }

            setOnTouchListener { v, event ->
                if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) && isDecrementing) {
                    isDecrementing = false
                }
                false
            }
        }

        setCounter(plant.count)
    }

    private fun setCounter(n: Int) {
        plant.count = if (n < 0) 0 else n
        counter.text = plant.count.toString()
        EventBus.getDefault().post(PlantCountChangedEvent())
    }

    inner class RptUpdater : Runnable {
        override fun run() {
            if (isIncrementing) {
                setCounter(++plant.count)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)

            } else if (isDecrementing) {
                setCounter(--plant.count)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)
            }
        }
    }


}