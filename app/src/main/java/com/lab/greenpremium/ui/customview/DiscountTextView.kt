package com.lab.greenpremium.ui.customview


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.lab.greenpremium.R

class DiscountTextView : TextView {

    private var dividerColor: Int = 0
    private var paint: Paint? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    private fun init(context: Context) {
        val resources = context.resources
        dividerColor = resources.getColor(R.color.green_3)

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = dividerColor
        paint!!.strokeWidth = resources.getDimension(R.dimen.line_thickness_2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), 0f, paint!!)
    }

}