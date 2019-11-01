
package com.midwestpilotcars.models.allDefaultModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PRICELIST implements Parcelable {

    @SerializedName("day_rate_price_range")
    private DayRatePriceRange mDayRatePriceRange;
    @SerializedName("no_goes_price_range")
    private NoGoesPriceRange mNoGoesPriceRange;
    @SerializedName("pay_per_mile_price_rangeData")
    private PayPerMilePriceRangeData mPayPerMilePriceRangeData;

    public PRICELIST() {

    }

    protected PRICELIST(Parcel in) {
    }

    public static final Creator<PRICELIST> CREATOR = new Creator<PRICELIST>() {
        @Override
        public PRICELIST createFromParcel(Parcel in) {
            return new PRICELIST(in);
        }

        @Override
        public PRICELIST[] newArray(int size) {
            return new PRICELIST[size];
        }
    };

    public DayRatePriceRange getDayRatePriceRange() {
        return mDayRatePriceRange;
    }

    public void setDayRatePriceRange(DayRatePriceRange dayRatePriceRange) {
        mDayRatePriceRange = dayRatePriceRange;
    }

    public NoGoesPriceRange getNoGoesPriceRange() {
        return mNoGoesPriceRange;
    }

    public void setNoGoesPriceRange(NoGoesPriceRange noGoesPriceRange) {
        mNoGoesPriceRange = noGoesPriceRange;
    }

    public PayPerMilePriceRangeData getPayPerMilePriceRangeData() {
        return mPayPerMilePriceRangeData;
    }

    public void setPayPerMilePriceRangeData(PayPerMilePriceRangeData payPerMilePriceRangeData) {
        mPayPerMilePriceRangeData = payPerMilePriceRangeData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
