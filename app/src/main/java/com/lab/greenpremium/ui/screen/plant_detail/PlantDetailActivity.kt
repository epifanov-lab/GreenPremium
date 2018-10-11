package com.lab.greenpremium.ui.screen.plant_detail

import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.data.repository.UserModel
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.ui.components.item.PlantItemCountControlsHelper
import com.lab.greenpremium.utills.currencyFormat
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_plant_detail.*
import kotlinx.android.synthetic.main.view_gallery_preview.*

class PlantDetailActivity : BaseActivity() {

    private lateinit var plant : Plant

    override fun layoutResId(): Int {
        return R.layout.activity_plant_detail
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        plant = UserModel.plants[intent.getIntExtra(KEY_OBJECT, 0)]

        button_back.setOnClickListener { onBackPressed() }

        button_cart.setOnClickListener {  }

        button_favorite.setOnClickListener {
            plant.isFavorite = !plant.isFavorite
            updateFavoriteButtonState(plant.isFavorite)
        }

        updateFavoriteButtonState(plant.isFavorite)

        text_title.text = plant.name
        plant.drawableResId?.let { image.setImageResource(it) }
        text_price.text = currencyFormat(plant.price)
        text_discount.text = currencyFormat(plant.discount)
        text_info_short.text = "${plant.info1}\n${plant.info2}"
        //text_info_long.text = plant.info2

        PlantItemCountControlsHelper(plant, text_counter, button_add, button_remove)

        setTouchAnimationShrink(image_1)
        setTouchAnimationShrink(image_2)
        setTouchAnimationShrink(image_else)
    }

    private fun updateFavoriteButtonState(isFavorite: Boolean) {
        button_favorite.setImageResource(if (isFavorite) R.drawable.ic_favorites_choosen else R.drawable.ic_favorites)
    }
}
