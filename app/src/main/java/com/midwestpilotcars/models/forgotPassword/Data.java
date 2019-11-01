
package com.midwestpilotcars.models.forgotPassword;

import com.google.gson.annotations.Expose;

public class Data {

    @Expose
    private String forgetPasswordUrl;

    public String getForgetPasswordUrl() {
        return forgetPasswordUrl;
    }

    public void setForgetPasswordUrl(String forgetPasswordUrl) {
        this.forgetPasswordUrl = forgetPasswordUrl;
    }

}
