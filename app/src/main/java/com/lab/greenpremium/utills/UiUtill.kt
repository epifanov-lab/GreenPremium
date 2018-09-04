package com.lab.greenpremium.utills

import android.graphics.*
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import com.lab.greenpremium.*
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

fun setTouchAnimationAlphaChange(view: View) {
    view.setOnTouchListener(View.OnTouchListener { p0, motionEvent ->
        val action = motionEvent?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                animateViewAlphaChange(view, ALPHA_VISIBLE, ALPHA_DISABLED)
                return@OnTouchListener true
            }

            MotionEvent.ACTION_UP -> {
                animateViewAlphaChange(view, ALPHA_DISABLED, ALPHA_VISIBLE)
                view.performClick()
                return@OnTouchListener true
            }

            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL -> {
                animateViewAlphaChange(view, ALPHA_DISABLED, ALPHA_VISIBLE)
                return@OnTouchListener false
            }
        }
        false
    })
}

fun setTouchAnimationShrink(view: View) {
    val onTouchListener = View.OnTouchListener { p0, motionEvent ->
        val action = motionEvent?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                LogUtil.i("ACTION_DOWN")
                animateViewShrink(view, SCALE_FULL, SCALE_PRESSED)
                return@OnTouchListener true
            }

            MotionEvent.ACTION_UP -> {
                LogUtil.i("ACTION_UP")
                animateViewShrink(view, SCALE_PRESSED, SCALE_FULL)
                return@OnTouchListener true
            }

            MotionEvent.ACTION_MOVE -> {
                LogUtil.i("ACTION_MOVE")
                //animateViewShrink(view, SCALE_PRESSED, SCALE_FULL)
                return@OnTouchListener false
            }
        }
        false
    }

    view.setOnTouchListener(onTouchListener)
}

private fun animateViewAlphaChange(view: View, fromAlpha: Float, toAlpha: Float) {
    val animation = AlphaAnimation(fromAlpha, toAlpha)
    animation.duration = DURATION_FAST
    animation.fillAfter = true
    view.startAnimation(animation)
}


private fun animateViewShrink(view: View, fromScale: Float, toScale: Float) {
    val animation = ScaleAnimation(fromScale, toScale, fromScale, toScale, (view.width / 2).toFloat(), (view.height / 2).toFloat())
    animation.duration = DURATION_FAST
    animation.fillAfter = true
    view.startAnimation(animation)
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