package com.midwestpilotcars.models.wallet;

import com.google.gson.annotations.SerializedName;

public class AllMoneyRequest {
    @SerializedName("wallet_amount")
    String wallet_amount;
    @SerializedName("wallet_comments")
    String wallet_comment;
    @SerializedName("wallet_driver_id")
    String driver_id;
    @SerializedName("wallet_created_on")
    String created_on;
    @SerializedName("wallet_id")
    String wallet_id;
    @SerializedName("status")
    String status;

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getWallet_comment() {
        return wallet_comment;
    }

    public void setWallet_comment(String wallet_comment) {
        this.wallet_comment = wallet_comment;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
