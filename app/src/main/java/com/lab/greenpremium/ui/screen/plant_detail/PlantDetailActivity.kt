package com.lab.greenpremium.ui.screen.plant_detail

import android.view.View
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Offer
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.ui.components.item.PlantItemCountControlsHelper
import com.lab.greenpremium.ui.screen.base.BaseActivity
import com.lab.greenpremium.utills.currencyFormat
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_plant_detail.*
import kotlinx.android.synthetic.main.view_gallery_preview.*
import javax.inject.Inject

class PlantDetailActivity : BaseActivity(), PlantDetailContract.View {

    @Inject
    internal lateinit var presenter: PlantDetailPresenter

    private lateinit var product: Product
    private lateinit var offer: Offer

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
        offer = product.offers[product.selectedOfferPosition]

        button_back.setOnClickListener { onBackPressed() }

        button_cart.setOnClickListener { }

        button_favorite.setOnClickListener {
            product.isFavorite = !product.isFavorite
            updateFavoriteButtonState(product.isFavorite)
        }

        updateFavoriteButtonState(product.isFavorite)

        text_title.text = product.name

        product.photo?.url?.let { url ->
            Glide.with(applicationContext)
                    .load(url)
                    .into(image)
        }

        offer.price?.let { text_price.text = currencyFormat(offer.price) }

        offer.old_price?.let {
            text_discount.visibility = View.VISIBLE
            text_discount.text = currencyFormat(offer.old_price)
        }

        setupInfoBlock()

        PlantItemCountControlsHelper(product, text_counter, button_add, button_remove)

        setTouchAnimationShrink(image_1)
        setTouchAnimationShrink(image_2)
        setTouchAnimationShrink(image_else)
    }

    private fun updateFavoriteButtonState(isFavorite: Boolean) {
        button_favorite.setImageResource(if (isFavorite) R.drawable.ic_favorites_choosen else R.drawable.ic_favorites)
    }

    private fun setupInfoBlock() {
        //У крупномеров может быть несколько оферов, в отличии от остальных типов растений
        val isLargePlant = product.offers.size > 1

        if (isLargePlant) {
            offer.crown_width?.let {
                text_info_short_1.visibility = VISIBLE
                text_info_short_1.text = applicationContext.getString(R.string.template_s_s,
                        offer.crown_width.name, offer.crown_width.value)
            }

            offer.height?.let {
                text_info_short_2.visibility = VISIBLE
                text_info_short_2.text = applicationContext.getString(R.string.template_s_s,
                        offer.height.name, offer.height.value)
            }

        } else {
            offer.crown_width?.let {
                text_info_short_1.visibility = VISIBLE
                text_info_short_1.text = applicationContext.getString(R.string.template_s_s,
                        offer.crown_width.name, offer.crown_width.value)
            }

            offer.item_height?.let {
                text_info_short_2.visibility = VISIBLE
                text_info_short_2.text = applicationContext.getString(R.string.template_s_s,
                        offer.item_height.name, offer.item_height.value)
            }
        }

        text_info_long.text = product.detail_text
    }
}
