package com.midwestpilotcars.models.wallet;

import com.google.gson.annotations.SerializedName;

public class WalletData {
    @SerializedName("approved_wallet_amount")
    String approve_amount;
    @SerializedName("pending_approval_wallet_amount")
    String pending_amount;
    @SerializedName("job_cash_in_advance")
    String cash_advance;
    @SerializedName("give_back_cash")
    String cash_back;
    @SerializedName("main_wallet_amount")
    String wallet_amount;

    public String getApprove_amount() {
        return approve_amount;
    }

    public void setApprove_amount(String approve_amount) {
        this.approve_amount = approve_amount;
    }

    public String getPending_amount() {
        return pending_amount;
    }

    public void setPending_amount(String pending_amount) {
        this.pending_amount = pending_amount;
    }

    public String getCash_advance() {
        return cash_advance;
    }

    public void setCash_advance(String cash_advance) {
        this.cash_advance = cash_advance;
    }

    public String getCash_back() {
        return cash_back;
    }

    public void setCash_back(String cash_back) {
        this.cash_back = cash_back;
    }

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }
}
