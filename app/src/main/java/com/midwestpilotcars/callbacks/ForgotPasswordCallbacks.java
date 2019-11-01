package com.midwestpilotcars.callbacks;

import com.midwestpilotcars.models.forgotPassword.ForgotPasswordResponseModel;

public interface ForgotPasswordCallbacks {
    void passwordRecoverSuccess(ForgotPasswordResponseModel forgotPasswordResponseModel);
    void passwordRecoverFailure(Throwable ex);
}
