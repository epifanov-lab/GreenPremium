package com.lab.greenpremium.utills

import android.graphics.*
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import com.lab.greenpremium.*
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.utills.eventbus.PlantCountChangedEvent
import org.greenrobot.eventbus.EventBus

const val CLICK_ACTION_THRESHOLD = 200


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
    view.setOnTouchListener(View.OnTouchListener { v, event ->
        val action = event?.action
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

private fun animateViewAlphaChange(view: View, fromAlpha: Float, toAlpha: Float) {
    val animation = AlphaAnimation(fromAlpha, toAlpha)
    animation.duration = DURATION_FAST
    animation.fillAfter = true
    view.startAnimation(animation)
}

fun setTouchAnimationShrink(view: View, onAnimationEndListener: OnAnimationEndListener? = null) {
    var startX = 0f
    var startY = 0f

    val onTouchListener = View.OnTouchListener { v, event ->
        val action = event?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                animateViewShrink(view, SCALE_FULL, SCALE_PRESSED)
                return@OnTouchListener true
            }

            MotionEvent.ACTION_UP -> {
                val endX = event.x
                val endY = event.y
                if (isAClick(startX, endX, startY, endY)) {
                    animateViewShrink(view, SCALE_PRESSED, SCALE_FULL, DURATION_VERY_FAST,
                            onAnimationEndListener ?: object : OnAnimationEndListener {
                                override fun onAnimationEndEvent() {
                                    view.performClick()
                                }
                            })
                }
                return@OnTouchListener true
            }

            MotionEvent.ACTION_MOVE -> {
                val endX = event.x
                val endY = event.y
                if (!isAClick(startX, endX, startY, endY)) {
                    animateViewShrink(view, SCALE_PRESSED, SCALE_FULL, DURATION_VERY_FAST)
                }
                return@OnTouchListener true
            }
        }
        false
    }

    view.setOnTouchListener(onTouchListener)
}


private fun animateViewShrink(view: View, fromScale: Float, toScale: Float, duration: Long = DURATION_FAST, onAnimationEndListener: OnAnimationEndListener? = null) {
    val animation = ScaleAnimation(fromScale, toScale, fromScale, toScale, (view.width / 2).toFloat(), (view.height / 2).toFloat())
    animation.duration = duration
    animation.fillAfter = true
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {
            //ignore
        }

        override fun onAnimationEnd(p0: Animation?) {
            onAnimationEndListener?.onAnimationEndEvent()
        }

        override fun onAnimationStart(p0: Animation?) {
            //ignore
        }
    })
    view.startAnimation(animation)
}

private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float): Boolean {
    val differenceX = Math.abs(startX - endX)
    val differenceY = Math.abs(startY - endY)
    return !/* =5 */(differenceX > CLICK_ACTION_THRESHOLD || differenceY > CLICK_ACTION_THRESHOLD)
}

interface OnAnimationEndListener {
    fun onAnimationEndEvent()
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