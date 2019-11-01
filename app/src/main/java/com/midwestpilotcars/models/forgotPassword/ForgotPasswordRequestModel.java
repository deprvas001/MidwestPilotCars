
package com.midwestpilotcars.models.forgotPassword;

import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequestModel {

    @SerializedName("user_name")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
