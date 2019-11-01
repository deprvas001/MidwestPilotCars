
package com.midwestpilotcars.models.allDefaultModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class AllDefaultDataModel implements Parcelable {

    @SerializedName("data")
    private Data mData;
    @SerializedName("status")
    private Long mStatus;

    AllDefaultDataModel() {

    }

    protected AllDefaultDataModel(Parcel in) {
        if (in.readByte() == 0) {
            mStatus = null;
        } else {
            mStatus = in.readLong();
        }
    }

    public static final Creator<AllDefaultDataModel> CREATOR = new Creator<AllDefaultDataModel>() {
        @Override
        public AllDefaultDataModel createFromParcel(Parcel in) {
            return new AllDefaultDataModel(in);
        }

        @Override
        public AllDefaultDataModel[] newArray(int size) {
            return new AllDefaultDataModel[size];
        }
    };

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mStatus == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mStatus);
        }
    }
}
