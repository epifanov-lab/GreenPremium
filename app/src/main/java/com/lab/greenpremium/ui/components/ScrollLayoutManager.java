package com.lab.greenpremium.ui.components;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;


public class ScrollLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public ScrollLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
