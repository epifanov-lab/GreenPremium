package com.lab.greenpremium.ui.screen.main.profile

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lab.greenpremium.data.entity.Event
import com.lab.greenpremium.ui.customview.item.EventItemView


class EventsRecyclerAdapter(private val list: List<Event>) : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = EventItemView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.event = list[position]
        holder.view.setNum(position)
        holder.view.hideLineConnector(position == list.lastIndex)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val view: EventItemView) : RecyclerView.ViewHolder(view)
}