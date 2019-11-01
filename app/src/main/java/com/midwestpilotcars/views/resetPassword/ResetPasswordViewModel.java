package com.midwestpilotcars.views.resetPassword;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.resetPassword.ResetPasswordRequestModel;
import com.midwestpilotcars.views.login.LoginRepository;

public class ResetPasswordViewModel extends AndroidViewModel {
    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CommonResponse> resetPassword(Context context, ResetPasswordRequestModel resetPasswordRequestModel) {
        return ResetPasswordRepository.getInstance().resetPassword(context, resetPasswordRequestModel);
    }
}
