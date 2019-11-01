
package com.midwestpilotcars.models.resetPassword;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequestModel {

    @SerializedName("new_password")
    private String newPassword;
    @SerializedName("old_password")
    private String oldPassword;
    @SerializedName("user_name")
    private String userName;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
