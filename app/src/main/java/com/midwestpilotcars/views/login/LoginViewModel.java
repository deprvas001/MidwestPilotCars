package com.midwestpilotcars.views.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

public class LoginViewModel extends AndroidViewModel {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<Boolean> loginUser(Context context, String userName, String password){
        //This will go to LoginRepository class which has method loginUser and pass username and password
        return LoginRepository.getInstance().loginUser(context,userName,password);
    }
}
