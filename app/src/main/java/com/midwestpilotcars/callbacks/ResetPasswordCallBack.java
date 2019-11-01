package com.midwestpilotcars.callbacks;

import com.midwestpilotcars.models.resetPassword.ResetPasswordResponseModel;

public interface ResetPasswordCallBack {
    void resetPasswordSuccess(ResetPasswordResponseModel resetPasswordResponseModel);
    void resetPasswordFailure(Throwable ex);
    void onAuthFailure();
}
