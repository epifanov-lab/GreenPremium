package com.lab.greenpremium.ui.components.assimmetric_recycler;

import android.support.v7.widget.RecyclerView;

public abstract class AGVRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {
  public abstract AsymmetricItem getItem(int position);
}
