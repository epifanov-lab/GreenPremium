package com.lab.greenpremium.ui.components.assimmetric_recycler.custom;

import android.view.ViewGroup;

import com.lab.greenpremium.ui.components.assimmetric_recycler.AGVRecyclerViewAdapter;
import com.lab.greenpremium.ui.components.assimmetric_recycler.AsymmetricItem;
import com.lab.greenpremium.utills.UiUtillKt;

import java.util.List;

public class AgvPhotoRecyclerViewAdapter extends AGVRecyclerViewAdapter<AgvPhotoViewHolder> {
    private final List<AgvPhotoItem> items;
    private final AgvPhotoClickListener listener;

    public AgvPhotoRecyclerViewAdapter(List<AgvPhotoItem> items, AgvPhotoClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public AgvPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AgvPhotoViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(AgvPhotoViewHolder holder, int position) {
        holder.bind(items.get(position));

        UiUtillKt.setTouchAnimationShrink(holder.imageView, () -> {
            if (listener != null) {
                listener.onClick(position, items.get(position).getPhoto());
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AsymmetricItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 1 : 0;
    }
}

