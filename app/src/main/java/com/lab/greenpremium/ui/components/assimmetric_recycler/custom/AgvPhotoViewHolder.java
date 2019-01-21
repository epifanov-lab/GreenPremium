package com.lab.greenpremium.ui.components.assimmetric_recycler.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lab.greenpremium.R;

public class AgvPhotoViewHolder extends RecyclerView.ViewHolder {
    protected final ImageView imageView;
    private Context context;

    AgvPhotoViewHolder(ViewGroup parent, int viewType) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_portfolio_item, parent, false));
        context = parent.getContext();
        imageView = itemView.findViewById(R.id.image);
    }

    void bind(AgvPhotoItem item) {
        Glide.with(context)
                .load(item.getPhoto().getUrl())
                .into(imageView);
    }
}
