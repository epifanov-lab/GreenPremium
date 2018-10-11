package com.lab.greenpremium.utills

import android.app.Activity
import android.app.Service
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import com.lab.greenpremium.*

fun requestFocusAndShowKeyboard(context: Context, view: View?) {
    if (view != null) {
        view.requestFocus()
        val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}

fun hideKeyboard(context: Context) {
    if (context is Activity) {
        val view = context.currentFocus
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

interface KeyboardListener {
    fun onKeyboardOpened()
    fun onKeyboardClosed()
}

fun setKeyboardListener(container: ViewGroup, listener: KeyboardListener) {
    container.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        container.getWindowVisibleDisplayFrame(r)
        val screenHeight = container.rootView.height

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight = screenHeight - r.bottom

        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
            listener.onKeyboardOpened()

            if (container is ScrollView) {
                Handler().post { container.smoothScrollTo(0, keypadHeight) }
            }

        } else {
            Handler().post { listener.onKeyboardClosed() }
        }
    }
}

fun expand(v: ViewGroup) {
    v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = v.measuredHeight

    v.layoutParams.height = 0
    v.visibility = View.VISIBLE
    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            v.layoutParams.height = if (interpolatedTime == 1f)
                ViewGroup.LayoutParams.WRAP_CONTENT
            else
                (targetHeight * interpolatedTime).toInt()
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong() // 1 dp/ms
    v.startAnimation(a)
}

fun collapse(v: ViewGroup) {
    val initialHeight = v.measuredHeight

    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                v.visibility = View.GONE
            } else {
                v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong() // 1 dp/ms
    v.startAnimation(a)
}

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
    view.setOnTouchListener(View.OnTouchListener { _, event ->
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

    val onTouchListener = View.OnTouchListener { _, event ->
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

            MotionEvent.ACTION_CANCEL -> {
                animateViewShrink(view, SCALE_PRESSED, SCALE_FULL, DURATION_VERY_FAST)
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

fun getScreenWidth(context: Context): Int {
    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun getScreenWidthPx(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

fun getScreenHeightPx(context: Context): Int {
    return context.resources.displayMetrics.heightPixels
}