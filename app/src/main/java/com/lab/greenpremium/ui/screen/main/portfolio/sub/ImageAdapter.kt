package com.lab.greenpremium.ui.screen.main.portfolio.sub

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Photo


class ImageAdapter(private val context: Context,
                   private val photos: List<Photo>) : BaseAdapter() {

    override fun getCount(): Int {
        return photos.size
    }

    override fun getItem(position: Int): Photo {
        return photos[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    // create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val photo = photos[position]
        val imageView: ImageView

        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(500, 500)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setPadding(0, context.resources.getDimensionPixelSize(R.dimen.space_28), 0, 0)

        } else {
            imageView = convertView as ImageView
        }

        Glide.with(context)
                .load(photo.url)
                .into(imageView)

        return imageView
    }

}
