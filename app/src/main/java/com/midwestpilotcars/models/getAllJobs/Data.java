
package com.midwestpilotcars.models.getAllJobs;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

    @SerializedName("COMPLETE")
    private ArrayList<COMPLETE> cOMPLETE;
    @SerializedName("ONGOING")
    private ArrayList<ONGOING> oNGOING;
    @SerializedName("UPCOMING")
    private ArrayList<UPCOMING> uPCOMING;

    protected Data(Parcel in) {
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

    public ArrayList<COMPLETE> getCOMPLETE() {
        return cOMPLETE;
    }

    public void setCOMPLETE(ArrayList<COMPLETE> cOMPLETE) {
        this.cOMPLETE = cOMPLETE;
    }

    public ArrayList<ONGOING> getONGOING() {
        return oNGOING;
    }

    public void setONGOING(ArrayList<ONGOING> oNGOING) {
        this.oNGOING = oNGOING;
    }

    public ArrayList<UPCOMING> getUPCOMING() {
        return uPCOMING;
    }

    public void setUPCOMING(ArrayList<UPCOMING> uPCOMING) {
        this.uPCOMING = uPCOMING;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
