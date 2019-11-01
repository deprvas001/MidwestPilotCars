
package com.midwestpilotcars.models.allDefaultModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TRUCKINGCOMPANY  implements Parcelable {

    @SerializedName("trucking_company_email")
    private String mTruckingCompanyEmail;
    @SerializedName("trucking_company_id")
    private String mTruckingCompanyId;
    @SerializedName("trucking_company_logo")
    private String mTruckingCompanyLogo;
    @SerializedName("trucking_company_name")
    private String mTruckingCompanyName;
    @SerializedName("trucking_company_phone")
    private String mTruckingCompanyPhone;

    protected TRUCKINGCOMPANY(Parcel in) {
        mTruckingCompanyEmail = in.readString();
        mTruckingCompanyId = in.readString();
        mTruckingCompanyLogo = in.readString();
        mTruckingCompanyName = in.readString();
        mTruckingCompanyPhone = in.readString();
    }

    public static final Creator<TRUCKINGCOMPANY> CREATOR = new Creator<TRUCKINGCOMPANY>() {
        @Override
        public TRUCKINGCOMPANY createFromParcel(Parcel in) {
            return new TRUCKINGCOMPANY(in);
        }

        @Override
        public TRUCKINGCOMPANY[] newArray(int size) {
            return new TRUCKINGCOMPANY[size];
        }
    };

    public String getTruckingCompanyEmail() {
        return mTruckingCompanyEmail;
    }

    public void setTruckingCompanyEmail(String truckingCompanyEmail) {
        mTruckingCompanyEmail = truckingCompanyEmail;
    }

    public String getTruckingCompanyId() {
        return mTruckingCompanyId;
    }

    public void setTruckingCompanyId(String truckingCompanyId) {
        mTruckingCompanyId = truckingCompanyId;
    }

    public String getTruckingCompanyLogo() {
        return mTruckingCompanyLogo;
    }

    public void setTruckingCompanyLogo(String truckingCompanyLogo) {
        mTruckingCompanyLogo = truckingCompanyLogo;
    }

    public String getTruckingCompanyName() {
        return mTruckingCompanyName;
    }

    public void setTruckingCompanyName(String truckingCompanyName) {
        mTruckingCompanyName = truckingCompanyName;
    }

    public String getTruckingCompanyPhone() {
        return mTruckingCompanyPhone;
    }

    public void setTruckingCompanyPhone(String truckingCompanyPhone) {
        mTruckingCompanyPhone = truckingCompanyPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTruckingCompanyEmail);
        dest.writeString(mTruckingCompanyId);
        dest.writeString(mTruckingCompanyLogo);
        dest.writeString(mTruckingCompanyName);
        dest.writeString(mTruckingCompanyPhone);
    }
}
