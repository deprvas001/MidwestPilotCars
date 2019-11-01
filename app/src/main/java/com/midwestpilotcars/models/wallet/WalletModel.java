package com.midwestpilotcars.models.wallet;

import com.midwestpilotcars.models.DayAddExpense;

public class WalletModel {
    String status;
    String message;
    WalletData data;

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

    public WalletData getData() {
        return data;
    }

    public void setData(WalletData data) {
        this.data = data;
    }
}
