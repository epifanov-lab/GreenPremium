package com.lab.greenpremium.ui.components.assimmetric_recycler.custom;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.lab.greenpremium.data.entity.Photo;
import com.lab.greenpremium.ui.components.assimmetric_recycler.AsymmetricItem;


public class AgvPhotoItem implements AsymmetricItem {
    private int columnSpan;
    private int rowSpan;
    private int position;
    private Photo photo;

    public AgvPhotoItem(int columnSpan, int rowSpan, int position, Photo photo) {
        this.columnSpan = columnSpan;
        this.rowSpan = rowSpan;
        this.position = position;
        this.photo = photo;
    }

    public AgvPhotoItem(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int getColumnSpan() {
        return columnSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }

    public int getPosition() {
        return position;
    }

    public Photo getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return String.format("%s: %sx%s", position, rowSpan, columnSpan);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcel(Parcel in) {
        columnSpan = in.readInt();
        rowSpan = in.readInt();
        position = in.readInt();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(columnSpan);
        dest.writeInt(rowSpan);
        dest.writeInt(position);
    }

    /* Parcelable interface implementation */
    public static final Parcelable.Creator<AgvPhotoItem> CREATOR = new Parcelable.Creator<AgvPhotoItem>() {
        @Override
        public AgvPhotoItem createFromParcel(@NonNull Parcel in) {
            return new AgvPhotoItem(in);
        }

        @Override
        @NonNull
        public AgvPhotoItem[] newArray(int size) {
            return new AgvPhotoItem[size];
        }
    };
}
