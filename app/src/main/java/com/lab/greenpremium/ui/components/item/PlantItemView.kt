package com.lab.greenpremium.ui.components.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.utills.PlantItemCountControlsHelper
import com.lab.greenpremium.utills.currencyFormat
import kotlinx.android.synthetic.main.view_item_plant.view.*


class PlantItemView : RelativeLayout {

    private lateinit var plant: Plant

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        LayoutInflater.from(context).inflate(R.layout.view_item_plant, this, true)

    }

    fun setData(plant: Plant) {
        this.plant = plant

        text_name.text = plant.name
        text_info_1.text = plant.info1
        text_info_2.text = plant.info2
        text_price.text = currencyFormat(plant.price)
        text_discount.text = currencyFormat(plant.discount)
        plant.drawableResId?.let { image.setImageResource(it) }

        updateByType(plant.type)

        PlantItemCountControlsHelper(plant, text_counter, button_add, button_remove)

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

    private fun showHeightSelector(enabled: Boolean) {
        height_selector.visibility = if (enabled) View.VISIBLE else View.GONE
        space.visibility = if (enabled) View.VISIBLE else View.GONE
        height_selector.text = "15 м" //TODO прибрать
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        p.setMargins(left, top, right, bottom)
        this.layoutParams = p
        this.requestLayout()
    }

    override fun setOnClickListener(onClickListener: OnClickListener) {
        container_image.setOnClickListener(onClickListener)
        container_info.setOnClickListener(onClickListener)
    }
}