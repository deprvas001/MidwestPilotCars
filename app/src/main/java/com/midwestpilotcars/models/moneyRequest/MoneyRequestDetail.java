package com.midwestpilotcars.models.moneyRequest;

import com.google.gson.annotations.SerializedName;

public class MoneyRequestDetail {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
