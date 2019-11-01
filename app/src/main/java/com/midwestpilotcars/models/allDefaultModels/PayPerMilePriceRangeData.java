
package com.midwestpilotcars.models.allDefaultModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PayPerMilePriceRangeData implements Parcelable {

    @SerializedName("auto_price")
    private String mAutoPrice;
    @SerializedName("end_price")
    private String mEndPrice;
    @SerializedName("start_price")
    private String mStartPrice;

    protected PayPerMilePriceRangeData(Parcel in) {
        mAutoPrice = in.readString();
        mEndPrice = in.readString();
        mStartPrice = in.readString();
    }

    public static final Creator<PayPerMilePriceRangeData> CREATOR = new Creator<PayPerMilePriceRangeData>() {
        @Override
        public PayPerMilePriceRangeData createFromParcel(Parcel in) {
            return new PayPerMilePriceRangeData(in);
        }

        @Override
        public PayPerMilePriceRangeData[] newArray(int size) {
            return new PayPerMilePriceRangeData[size];
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
