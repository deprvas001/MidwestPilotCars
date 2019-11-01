package com.midwestpilotcars.views.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.midwestpilotcars.models.CommonResponse;

public class HomeViewModel extends AndroidViewModel {
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<CommonResponse> getAllJobs(Context context,String userType,String userId,String jobType,int offset,int pageSize){
        return HomeRepository.getInstance().getAllJobs(context,userType,userId,jobType,offset,pageSize);
    }
}
