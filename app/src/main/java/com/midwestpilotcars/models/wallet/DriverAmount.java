package com.midwestpilotcars.models.wallet;

import com.google.gson.annotations.SerializedName;

public class DriverAmount {
    @SerializedName("approx_total_pay_to_driver")
    String approx_pay;
    @SerializedName("walletData")
    WalletData walletData;
    @SerializedName("AllDayData")
    AllDayAmount allDayAmount;

    public AllDayAmount getAllDayAmount() {
        return allDayAmount;
    }

    public void setAllDayAmount(AllDayAmount allDayAmount) {
        this.allDayAmount = allDayAmount;
    }


    public String getApprox_pay() {
        return approx_pay;
    }

    public WalletData getWalletData() {
        return walletData;
    }

    public void setWalletData(WalletData walletData) {
        this.walletData = walletData;
    }

    public void setApprox_pay(String approx_pay) {
        this.approx_pay = approx_pay;
    }
}

