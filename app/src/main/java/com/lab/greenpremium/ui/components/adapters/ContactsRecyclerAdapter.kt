package com.lab.greenpremium.ui.components.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lab.greenpremium.data.entity.Contact
import com.lab.greenpremium.ui.components.item.ContactItemView
import com.lab.greenpremium.utills.setTouchAnimationShrink


class ContactsRecyclerAdapter(private val list: List<Contact>, private val orientation: Int = LinearLayoutManager.VERTICAL, private val margin: Int?) : RecyclerView.Adapter<ContactsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ContactItemView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.contact = list[position]

        if (margin != null) {

            if (orientation == VERTICAL) {
                holder.view.setMargins(
                        0,
                        margin / 2,
                        0,
                        if (position == list.lastIndex) margin * 3 else margin / 2
                )

            } else if (orientation == HORIZONTAL) {
                holder.view.setMargins(
                        margin / 2,
                        margin / 2,
                        margin / 2,
                        margin / 2
                )
            }
        }

        setTouchAnimationShrink(holder.view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val view: ContactItemView) : RecyclerView.ViewHolder(view)
}