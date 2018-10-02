package com.lab.greenpremium.ui.components.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lab.greenpremium.data.entity.raw.Plant
import com.lab.greenpremium.ui.components.item.PlantItemView
import com.lab.greenpremium.utills.OnAnimationEndListener
import com.lab.greenpremium.utills.setTouchAnimationShrink


class PlantRecyclerAdapter(private val list: List<Plant>, private val margin: Int?, private val listener: OnPlantSelectedListener?) : RecyclerView.Adapter<PlantRecyclerAdapter.ViewHolder>() {


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

        setTouchAnimationShrink(holder.view, object : OnAnimationEndListener {
            override fun onAnimationEndEvent() {
                listener?.onPlantSelected(list[position])
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val view: PlantItemView) : RecyclerView.ViewHolder(view)

    interface OnPlantSelectedListener {
        fun onPlantSelected(plant: Plant)
    }
}