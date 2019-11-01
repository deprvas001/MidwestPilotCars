package com.midwestpilotcars.views.splash;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.midwestpilotcars.callbacks.DefaultDataCallBack;
import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.views.login.LoginRepository;

public class SplashViewModel extends AndroidViewModel {
    public SplashViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<CommonResponse> getDefaultData(Context context){
        return SplashRepository.getInstance().getDefaultData(context);
    }
}
