package com.example.irepeat.Utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class ParcelableRelativeLayout implements Parcelable {

    private int height;
    private int width;
    private View v;

    public ParcelableRelativeLayout(View relativeLayout) {
        this.height = relativeLayout.getLayoutParams().height;
        this.width = relativeLayout.getLayoutParams().width;
        this.v=relativeLayout;
    }

    protected ParcelableRelativeLayout(Parcel in) {
        height = in.readInt();
        width = in.readInt();
    }

    public static final Creator<ParcelableRelativeLayout> CREATOR = new Creator<ParcelableRelativeLayout>() {
        @Override
        public ParcelableRelativeLayout createFromParcel(Parcel in) {
            return new ParcelableRelativeLayout(in);
        }

        @Override
        public ParcelableRelativeLayout[] newArray(int size) {
            return new ParcelableRelativeLayout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public View getView() {
        return v;
    }
}
