package com.midwestpilotcars.views.forgotPassword;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordRequestModel;
import com.midwestpilotcars.views.login.LoginRepository;

public class ForgotPasswordViewModel extends AndroidViewModel {
    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<CommonResponse> recoverPassword(ForgotPasswordRequestModel forgotPasswordRequestModel){
        return ForgotPasswordRepository.getInstance().recoverPassword(forgotPasswordRequestModel);
    }
}
