package com.midwestpilotcars.views.login;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.midwestpilotcars.callbacks.LoginCallback;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.loginModels.LoginResponseModel;
import com.watcho.network.ApiUtils;

import org.jetbrains.annotations.NotNull;

public class LoginRepository {
    private static LoginRepository loginRepository = null;

    private LoginRepository() {

    }

    public static LoginRepository getInstance() {
        if (loginRepository == null)
            loginRepository = new LoginRepository();
        return loginRepository;
    }

    public MutableLiveData<Boolean> loginUser(Context context, String userName, String password) {
        MutableLiveData<Boolean> loginResponseLiveData = new MutableLiveData<>();
        ApiUtils apiUtils = new ApiUtils();
        apiUtils.loginUser(userName, password, new LoginCallback() {
            @Override
            public void onLoginSuccess(@NotNull LoginResponseModel loginResponseModel) {
                SharedPreferenceHelper.Companion.getInstance().saveUserData(context, loginResponseModel);
                loginResponseLiveData.postValue(true);
            }

            @Override
            public void onLoginFailure(@NotNull Throwable exception) {
                loginResponseLiveData.postValue(false);
            }
        });
        return loginResponseLiveData;
    }
}
