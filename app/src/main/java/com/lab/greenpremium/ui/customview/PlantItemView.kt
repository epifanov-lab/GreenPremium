package com.lab.greenpremium.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.utills.currencyFormat
import kotlinx.android.synthetic.main.item_plant_view.view.*


class PlantItemView : RelativeLayout {

    enum class Type {
        REGULAR;

        companion object {
            fun fromInt(value: Int): Type {
                for (type in Type.values()) {
                    if (value == type.ordinal) {
                        return type
                    }
                }
                return REGULAR
            }
        }
    }

    lateinit var type: Type

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.item_plant_view, this, true)

        attrs.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.PlantItemView)
            type = Type.fromInt(a.getInt(R.styleable.PlantItemView_type, Type.REGULAR.ordinal))
            a.recycle()
        }
    }

    fun setData(plant : Plant) {
        text_name.text = plant.name
        text_info_1.text = plant.info1
        text_info_2.text = plant.info2
        text_price.text = currencyFormat(plant.price)
        text_discount.text = currencyFormat(plant.discount)
    }
}