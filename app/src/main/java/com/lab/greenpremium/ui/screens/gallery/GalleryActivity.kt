package com.lab.greenpremium.ui.screens.gallery

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.lab.greenpremium.*
import com.lab.greenpremium.ui.screens.base.BaseActivity
import kotlinx.android.synthetic.main.activity_gallery.*
import javax.inject.Inject

class GalleryActivity : BaseActivity(), GalleryContract.View {

    @Inject
    internal lateinit var presenter: GalleryPresenter

    override fun layoutResId(): Int {
        return R.layout.activity_gallery
    }

    override fun initializeDaggerComponent() {
        DaggerGalleryComponent.builder()
                .appComponent((application as App).component)
                .galleryModule(GalleryModule(this))
                .build()
                .inject(this)
    }

    override fun initViews() {
        val photos = intent.getStringArrayListExtra(KEY_IMAGES_URLS_LIST)
        val chosenPhotoNum = intent.getIntExtra(KEY_CHOSEN_IMAGE_NUM, 0)

        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))

        recycler_gallery.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_gallery.adapter = ImageRecyclerAdapter(photos)
        LinearSnapHelper().attachToRecyclerView(recycler_gallery)
        (recycler_gallery.layoutManager as LinearLayoutManager).scrollToPosition(chosenPhotoNum)

        close.setOnClickListener { onBackPressed() }
    }

}
