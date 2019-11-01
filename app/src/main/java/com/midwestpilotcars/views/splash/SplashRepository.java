package com.midwestpilotcars.views.splash;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.internal.service.Common;
import com.midwestpilotcars.callbacks.DefaultDataCallBack;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.allDefaultModels.AllDefaultDataModel;
import com.watcho.network.ApiUtils;

import org.jetbrains.annotations.NotNull;

public class SplashRepository {
    private static SplashRepository splashRepository = null;

    private SplashRepository() {

    }

    public static SplashRepository getInstance() {
        if (splashRepository == null)
            splashRepository = new SplashRepository();
        return splashRepository;
    }

    public MutableLiveData<CommonResponse> getDefaultData(Context context) {
        MutableLiveData<CommonResponse> loginResponseLiveData = new MutableLiveData<>();
        ApiUtils apiUtils = new ApiUtils();
        CommonResponse commonResponse = new CommonResponse();
        apiUtils.getDefaultData(SharedPreferenceHelper.Companion.getInstance().getUserData(context).getData().getAuthenticateToken()
                , new DefaultDataCallBack() {
                    @Override
                    public void onAuthFailure() {
                        commonResponse.setStatus(false);
                        commonResponse.setData(null);
                        loginResponseLiveData.postValue(commonResponse);
                    }

                    @Override
                    public void onDefaultCallbackSuccess(@NotNull AllDefaultDataModel allDefaultDataModel) {
                        SharedPreferenceHelper.Companion.getInstance().saveDefaultData(context, allDefaultDataModel);
                        commonResponse.setStatus(true);
                        commonResponse.setData(allDefaultDataModel);
                        loginResponseLiveData.postValue(commonResponse);
                    }

                    @Override
                    public void onDefaultCallbackFailure(@NotNull Throwable exception) {
                        commonResponse.setStatus(false);
                        commonResponse.setData(exception);
                        loginResponseLiveData.postValue(commonResponse);
                    }
                });
        return loginResponseLiveData;
    }
}
