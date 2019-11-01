package com.midwestpilotcars.views.forgotPassword;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.midwestpilotcars.callbacks.ForgotPasswordCallbacks;
import com.midwestpilotcars.callbacks.LoginCallback;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordRequestModel;
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordResponseModel;
import com.midwestpilotcars.models.loginModels.LoginResponseModel;
import com.watcho.network.ApiUtils;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordRepository {
    private static ForgotPasswordRepository forgotPasswordRepository = null;

    private ForgotPasswordRepository() {

    }

    public static ForgotPasswordRepository getInstance() {
        if (forgotPasswordRepository == null)
            forgotPasswordRepository = new ForgotPasswordRepository();
        return forgotPasswordRepository;
    }

    public MutableLiveData<CommonResponse> recoverPassword(ForgotPasswordRequestModel forgotPasswordRequestModel) {
        MutableLiveData<CommonResponse> loginResponseLiveData = new MutableLiveData<>();
        ApiUtils apiUtils = new ApiUtils();
        CommonResponse commonResponse = new CommonResponse();
        apiUtils.recoverPassword(forgotPasswordRequestModel, new ForgotPasswordCallbacks() {
            @Override
            public void passwordRecoverSuccess(ForgotPasswordResponseModel forgotPasswordResponseModel) {
                commonResponse.setStatus(true);
                commonResponse.setData(forgotPasswordResponseModel);
                loginResponseLiveData.postValue(commonResponse);
            }

            @Override
            public void passwordRecoverFailure(Throwable ex) {
                commonResponse.setStatus(false);
                commonResponse.setData(ex);
                loginResponseLiveData.postValue(commonResponse);
            }
        });
        return loginResponseLiveData;
    }
}
