package com.lab.greenpremium.ui.screen.plant_detail

import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.components.item.PlantItemCountControlsHelper
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_plant_detail.*
import kotlinx.android.synthetic.main.view_gallery_preview.*
import javax.inject.Inject

class PlantDetailActivity : BaseActivity(), PlantDetailContract.View {

    @Inject
    internal lateinit var presenter: PlantDetailPresenter

    private lateinit var product: Product

    override fun layoutResId(): Int {
        return R.layout.activity_plant_detail
    }

    override fun initializeDaggerComponent() {
        DaggerPlantDetailComponent.builder()
                .appComponent((application as App).component)
                .plantDetailModule(PlantDetailModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        product = intent.getSerializableExtra(KEY_OBJECT) as Product

        button_back.setOnClickListener { onBackPressed() }

        button_cart.setOnClickListener { }

        button_favorite.setOnClickListener {
            product.isFavorite = !product.isFavorite
            updateFavoriteButtonState(product.isFavorite)
        }

        updateFavoriteButtonState(product.isFavorite)

        text_title.text = product.name
        //product.drawableResId?.let { image.setImageResource(it) }
        //text_price.text = currencyFormat(product.price)
        //text_discount.text = currencyFormat(product.discount)
        //text_info_short.text = "${product.info1}\n${product.info2}"
        //text_info_long.text = product.info2

        PlantItemCountControlsHelper(product, text_counter, button_add, button_remove)

        setTouchAnimationShrink(image_1)
        setTouchAnimationShrink(image_2)
        setTouchAnimationShrink(image_else)
    }

    private fun updateFavoriteButtonState(isFavorite: Boolean) {
        button_favorite.setImageResource(if (isFavorite) R.drawable.ic_favorites_choosen else R.drawable.ic_favorites)
    }
}
