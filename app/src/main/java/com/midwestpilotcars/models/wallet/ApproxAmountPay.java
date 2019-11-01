package com.midwestpilotcars.models.wallet;

public class ApproxAmountPay {
    String status;
    String message;
    DriverAmount data;

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

    public DriverAmount getData() {
        return data;
    }

    public void setData(DriverAmount data) {
        this.data = data;
    }
}
