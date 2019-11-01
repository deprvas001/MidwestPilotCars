
package com.midwestpilotcars.models.loginModels;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {

    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_password")
    private String userPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
