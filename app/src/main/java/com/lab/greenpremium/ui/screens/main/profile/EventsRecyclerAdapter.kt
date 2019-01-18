package com.lab.greenpremium.ui.screens.main.profile

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lab.greenpremium.data.entity.Event
import com.lab.greenpremium.ui.components.item.EventItemView


class EventsRecyclerAdapter(private val list: List<Event>,
                            private val listener: EventsRecyclerListener) : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = EventItemView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.view
        val event = list[position]
        view.event = event
        view.setNum(position)
        view.hideLineConnector(position == list.lastIndex)

        view.setOnClickPdfListener(View.OnClickListener { listener.onClickPdf(event.file_path) })

        if (position == list.lastIndex) {
            listener?.onRecyclerBottomReached(list.size)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val view: EventItemView) : RecyclerView.ViewHolder(view)

    interface EventsRecyclerListener {
        fun onClickPdf(file_path: String)
        fun onRecyclerBottomReached(size: Int)
    }
}