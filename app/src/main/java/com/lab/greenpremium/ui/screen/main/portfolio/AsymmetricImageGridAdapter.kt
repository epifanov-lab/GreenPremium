package com.lab.greenpremium.ui.screen.main.portfolio

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout.LayoutParams
import com.bumptech.glide.Glide
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.raw.Image
import com.lab.greenpremium.utills.setTouchAnimationShrink


class AsymmetricImageGridAdapter(private val context: Context, private val images: List<Image>) : RecyclerView.Adapter<AsymmetricImageGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ImageView(parent.context)
        view.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.beige))

        val scale = context.resources.displayMetrics.density
        val padding = (2 * scale + 0.5f).toInt()
        view.setPadding(padding, padding, padding, padding)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.view.layoutParams.width = image.width
        holder.view.layoutParams.height = image.height

        Glide.with(context)
                .load(image.url)
                .into(holder.view)

        setTouchAnimationShrink(holder.view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(val view: ImageView) : RecyclerView.ViewHolder(view)
}