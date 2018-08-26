package com.lab.greenpremium.ui.components.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout.LayoutParams
import com.bumptech.glide.Glide
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Image


class AsymmetricImageGridAdapter(private val context: Context, private val images: List<Image>) : RecyclerView.Adapter<AsymmetricImageGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ImageView(parent.context)
        view.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.beige))

        val scale = context.resources.displayMetrics.density
        val padding = (2 * scale + 0.5f).toInt()
        view.setPadding(padding, padding, padding,   padding)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
                .load(images[position].url)
                .into(holder.view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(val view: ImageView) : RecyclerView.ViewHolder(view)
}