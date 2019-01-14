package com.lab.greenpremium.ui.components.item

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Photo
import com.lab.greenpremium.utills.LogUtil
import kotlinx.android.synthetic.main.view_plant_photos_preview.view.*


class PlantPhotosPreviewView : ConstraintLayout {

    var gallery: List<Photo>? = null
        set(value) {
            LogUtil.i("GALLERY_PREVIEW data: $value")
            field = value
            updateView()
        }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_plant_photos_preview, this, true)
    }

    private fun updateView() {

        if (gallery == null || gallery!!.isEmpty()) {
            visibility = View.GONE
            return
        }

        if (gallery!!.isNotEmpty()) {
            image_1.visibility = View.VISIBLE
            Glide.with(context)
                    .load(gallery!![0].url)
                    .into(image_1)
        }

        if (gallery!!.size >= 2) {
            image_2.visibility = View.VISIBLE
            Glide.with(context)
                    .load(gallery!![1].url)
                    .into(image_2)
        }

        if (gallery!!.size >= 3) {
            image_else.visibility = View.VISIBLE
            image_else.text = "+${gallery!!.size - 2}"
        }
    }

    override fun setOnTouchListener(l: OnTouchListener?) {
        image_1.setOnTouchListener(l)
        image_2.setOnTouchListener(l)
        image_else.setOnTouchListener(l)
    }
}