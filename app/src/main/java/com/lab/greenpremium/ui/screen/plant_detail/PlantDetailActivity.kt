package com.lab.greenpremium.ui.screen.plant_detail

import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.data.repository.user.UserRepository
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.utills.PlantItemCountControlsHelper
import com.lab.greenpremium.utills.currencyFormat
import kotlinx.android.synthetic.main.activity_plant_detail.*

class PlantDetailActivity : BaseActivity() {

    private lateinit var plant : Plant

    override fun layoutResId(): Int {
        return R.layout.activity_plant_detail
    }

    override fun initializeDaggerComponent() {
        //ignore
    }

    override fun initViews() {
        plant = UserRepository.plants[intent.getIntExtra(KEY_OBJECT, 0)]

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
        //text_info_short.text = plant.info1
        //text_info_long.text = plant.info2

        PlantItemCountControlsHelper(plant, text_counter, button_add, button_remove)
    }

    private fun updateFavoriteButtonState(isFavorite: Boolean) {
        button_favorite.setImageResource(if (isFavorite) R.drawable.ic_favorites_checked else R.drawable.ic_favorites)
    }
}
