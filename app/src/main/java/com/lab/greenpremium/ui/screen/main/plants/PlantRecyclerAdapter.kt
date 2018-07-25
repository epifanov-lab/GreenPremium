package com.lab.greenpremium.ui.screen.main.plants

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lab.greenpremium.data.entity.Plant
import com.lab.greenpremium.ui.customview.item.PlantItemView


class PlantRecyclerAdapter(private val list: List<Plant>, private val margin: Int?) : RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PlantItemView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setData(list[position])

        if (margin != null) {
            holder.view.setMargins(
                    margin / 2,
                    margin / 2,
                    margin / 2,
                    if (position == list.lastIndex) margin * 3 else margin / 2)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val view: PlantItemView) : RecyclerView.ViewHolder(view)
}