package com.midwestpilotcars.views.resetPassword;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.midwestpilotcars.callbacks.LoginCallback;
import com.midwestpilotcars.callbacks.ResetPasswordCallBack;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.loginModels.LoginResponseModel;
import com.midwestpilotcars.models.resetPassword.ResetPasswordRequestModel;
import com.midwestpilotcars.models.resetPassword.ResetPasswordResponseModel;
import com.watcho.network.ApiUtils;

import org.jetbrains.annotations.NotNull;

public class ResetPasswordRepository {
    private static ResetPasswordRepository resetPasswordRepository = null;

    private ResetPasswordRepository() {

    }

    public static ResetPasswordRepository getInstance() {
        if (resetPasswordRepository == null)
            resetPasswordRepository = new ResetPasswordRepository();
        return resetPasswordRepository;
    }

    public MutableLiveData<CommonResponse> resetPassword(Context context, ResetPasswordRequestModel resetPasswordRequestModel) {
        MutableLiveData<CommonResponse> commonResponseMutableLiveData = new MutableLiveData<>();
        CommonResponse commonResponse = new CommonResponse();
        ApiUtils apiUtils = new ApiUtils();
        apiUtils.resetPassword(SharedPreferenceHelper.Companion.getInstance().getUserData(context).getData().getAuthenticateToken(),resetPasswordRequestModel, new ResetPasswordCallBack() {
            @Override
            public void resetPasswordSuccess(ResetPasswordResponseModel resetPasswordResponseModel) {
                commonResponse.setStatus(true);
                commonResponse.setData(resetPasswordResponseModel);
                commonResponseMutableLiveData.postValue(commonResponse);
            }

            @Override
            public void resetPasswordFailure(Throwable ex) {
                commonResponse.setStatus(false);
                commonResponse.setData(ex);
                commonResponseMutableLiveData.postValue(commonResponse);
            }

            @Override
            public void onAuthFailure() {
                commonResponse.setStatus(false);
                commonResponse.setData(null);
                commonResponseMutableLiveData.postValue(commonResponse);
            }
        });
        return commonResponseMutableLiveData;
    }
}
