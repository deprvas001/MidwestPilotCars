package com.midwestpilotcars.models.addDayExpenses;

import android.support.v4.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

public class DayDetails {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String messsage;
    @SerializedName("data")
    private DataPerDay data;

    public String getStatus() {
        return status;
    }

    public DataPerDay getData() {
        return data;
    }

    public void setData(DataPerDay data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
