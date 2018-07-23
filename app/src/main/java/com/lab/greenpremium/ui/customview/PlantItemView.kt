package com.lab.greenpremium.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.utills.currencyFormat
import kotlinx.android.synthetic.main.view_item_plant.view.*

const val MIN_COUNT = 0

class PlantItemView : RelativeLayout {

    private var count: Int = MIN_COUNT

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.view_item_plant, this, true)

        button_add.setOnClickListener { setCounter(++count) }
        button_remove.setOnClickListener { setCounter(--count) }

    }

    private fun setCounter(n: Int) {
        count = if (n < 0) 0 else n
        text_counter.text = count.toString()
    }

    private fun updateByType(type: Plant.Type) {
        when (type) {
            Plant.Type.LIVING, Plant.Type.ARTIFICIAL -> {
                showHeightSelector(false)
            }

            Plant.Type.BIG -> {
                text_info_2.text = context.getText(R.string.title_height)
                showHeightSelector(true)
            }
        }
    }

    private fun showHeightSelector(enabled : Boolean) {
        height_selector.visibility = if (enabled) View.VISIBLE else View.GONE
        space.visibility = if (enabled) View.VISIBLE else View.GONE
        height_selector.text = "15 м" //TODO прибрать
    }


    fun setData(plant: Plant) {
        text_name.text = plant.name
        text_info_1.text = plant.info1
        text_info_2.text = plant.info2
        text_price.text = currencyFormat(plant.price)
        text_discount.text = currencyFormat(plant.discount)
        updateByType(plant.type)
        setCounter(count)
    }
}