
package com.midwestpilotcars.models.allDefaultModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

    @SerializedName("LOADTYPE")
    private ArrayList<LOADTYPE> mLOADTYPE;
    @SerializedName("PRICELIST")
    private PRICELIST mPRICELIST;
    @SerializedName("TRUCKINGCOMPANY")
    private ArrayList<TRUCKINGCOMPANY> mTRUCKINGCOMPANY;

    protected Data(Parcel in) {
        mLOADTYPE = in.createTypedArrayList(LOADTYPE.CREATOR);
        mPRICELIST = in.readParcelable(PRICELIST.class.getClassLoader());
        mTRUCKINGCOMPANY = in.createTypedArrayList(TRUCKINGCOMPANY.CREATOR);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public ArrayList<LOADTYPE> getLOADTYPE() {
        return mLOADTYPE;
    }

    public void setLOADTYPE(ArrayList<LOADTYPE> lOADTYPE) {
        mLOADTYPE = lOADTYPE;
    }

    public PRICELIST getPRICELIST() {
        return mPRICELIST;
    }

    public void setPRICELIST(PRICELIST pRICELIST) {
        mPRICELIST = pRICELIST;
    }

    public ArrayList<TRUCKINGCOMPANY> getTRUCKINGCOMPANY() {
        return mTRUCKINGCOMPANY;
    }

    public void setTRUCKINGCOMPANY(ArrayList<TRUCKINGCOMPANY> tRUCKINGCOMPANY) {
        mTRUCKINGCOMPANY = tRUCKINGCOMPANY;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mLOADTYPE);
        dest.writeParcelable(mPRICELIST, flags);
        dest.writeTypedList(mTRUCKINGCOMPANY);
    }
}
