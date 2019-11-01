
package com.midwestpilotcars.models.allDefaultModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LOADTYPE implements Parcelable {

    @SerializedName("load_type_description")
    private String mLoadTypeDescription;
    @SerializedName("load_type_id")
    private String mLoadTypeId;
    @SerializedName("load_type_name")
    private String mLoadTypeName;

    protected LOADTYPE(Parcel in) {
        mLoadTypeDescription = in.readString();
        mLoadTypeId = in.readString();
        mLoadTypeName = in.readString();
    }

    public static final Creator<LOADTYPE> CREATOR = new Creator<LOADTYPE>() {
        @Override
        public LOADTYPE createFromParcel(Parcel in) {
            return new LOADTYPE(in);
        }

        @Override
        public LOADTYPE[] newArray(int size) {
            return new LOADTYPE[size];
        }
    };

    public String getLoadTypeDescription() {
        return mLoadTypeDescription;
    }

    public void setLoadTypeDescription(String loadTypeDescription) {
        mLoadTypeDescription = loadTypeDescription;
    }

    public String getLoadTypeId() {
        return mLoadTypeId;
    }

    public void setLoadTypeId(String loadTypeId) {
        mLoadTypeId = loadTypeId;
    }

    public String getLoadTypeName() {
        return mLoadTypeName;
    }

    public void setLoadTypeName(String loadTypeName) {
        mLoadTypeName = loadTypeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLoadTypeDescription);
        dest.writeString(mLoadTypeId);
        dest.writeString(mLoadTypeName);
    }
}
