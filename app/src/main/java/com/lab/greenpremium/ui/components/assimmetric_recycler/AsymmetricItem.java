package com.lab.greenpremium.ui.components.assimmetric_recycler;

import android.os.Parcelable;

public interface AsymmetricItem extends Parcelable {
  int getColumnSpan();
  int getRowSpan();
}
