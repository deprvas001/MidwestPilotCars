
package com.midwestpilotcars.models.allDefaultModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NoGoesPriceRange implements Parcelable {

    @SerializedName("auto_price")
    private String mAutoPrice;
    @SerializedName("end_price")
    private String mEndPrice;
    @SerializedName("start_price")
    private String mStartPrice;

    protected NoGoesPriceRange(Parcel in) {
        mAutoPrice = in.readString();
        mEndPrice = in.readString();
        mStartPrice = in.readString();
    }

    public static final Creator<NoGoesPriceRange> CREATOR = new Creator<NoGoesPriceRange>() {
        @Override
        public NoGoesPriceRange createFromParcel(Parcel in) {
            return new NoGoesPriceRange(in);
        }

        @Override
        public NoGoesPriceRange[] newArray(int size) {
            return new NoGoesPriceRange[size];
        }
    };

    public String getAutoPrice() {
        return mAutoPrice;
    }

    public void setAutoPrice(String autoPrice) {
        mAutoPrice = autoPrice;
    }

    public String getEndPrice() {
        return mEndPrice;
    }

    public void setEndPrice(String endPrice) {
        mEndPrice = endPrice;
    }

    public String getStartPrice() {
        return mStartPrice;
    }

    public void setStartPrice(String startPrice) {
        mStartPrice = startPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAutoPrice);
        dest.writeString(mEndPrice);
        dest.writeString(mStartPrice);
    }
}
