package com.lab.greenpremium.ui.screens.plant_detail

import android.view.View
import com.bumptech.glide.Glide
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.OfferParam
import com.lab.greenpremium.data.entity.Photo
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.data.entity.getPhotosUrls
import com.lab.greenpremium.ui.components.item.PlantItemCountControlsHelper
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.utills.eventbus.BaseEvent
import com.lab.greenpremium.utills.eventbus.ProductQuantityChangedEvent
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.activity_plant_detail.*
import kotlinx.android.synthetic.main.view_plant_photos_preview.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class PlantDetailActivity : BaseActivity(), PlantDetailContract.View {

    //TODO photos -> gallery

    @Inject
    internal lateinit var presenter: PlantDetailPresenter

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
        presenter.onViewCreated(intent.getSerializableExtra(KEY_OBJECT) as Product)

        button_favorite.setOnClickListener { presenter.onClickFavorite() }
        button_back.setOnClickListener { onBackPressed() }
    }

    override fun setTitle(title: String) {
        text_title.text = title
    }

    override fun setPhoto(photo: Photo) {
        Glide.with(applicationContext)
                .load(photo.url)
                .into(image)
    }

    override fun updateFavoriteButtonState(isFavorite: Boolean) {
        button_favorite.setImageResource(if (isFavorite) R.drawable.ic_favorites_choosen else R.drawable.ic_favorites)
    }

    override fun initializeCounter(product: Product) {
        PlantItemCountControlsHelper(product, text_counter, button_add, button_remove)
    }

    override fun setPrice(price: String?, oldPrice: String?) {
        text_price.text = price
        oldPrice?.let {
            text_discount.visibility = View.VISIBLE
            text_discount.text = oldPrice
        }
    }

    override fun setShortInfo(params: Array<OfferParam?>) {
        var text = ""
        params.forEach { param -> param?.let { text += "$it\n" } }
        text_info_short.text = text
    }

    override fun setLongInfo(text: String) {
        text_info_long.text = text
    }

    override fun initializeGallery(photos: List<Photo>) {
        gallery_preview.gallery = photos

        image_1.setOnClickListener { presenter.onClickImage(0) }
        image_2.setOnClickListener { presenter.onClickImage(1) }
        image_else.setOnClickListener { presenter.onClickImage(2) }

        setTouchAnimationShrink(image_1)
        setTouchAnimationShrink(image_2)
        setTouchAnimationShrink(image_else)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: BaseEvent) {
        when (event) {
            is ProductQuantityChangedEvent -> presenter.onProductQuantityChanged(event.product)
        }
    }

    override fun goToGalleryScreen(gallery: List<Photo>, pos: Int) {
        goToGalleryScreen(getPhotosUrls(gallery), pos)
    }
}