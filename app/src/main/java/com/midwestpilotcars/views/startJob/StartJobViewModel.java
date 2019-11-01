package com.midwestpilotcars.views.startJob;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.resetPassword.ResetPasswordRequestModel;
import com.midwestpilotcars.models.startJob.StartJobRequestModel;
import com.midwestpilotcars.views.resetPassword.ResetPasswordRepository;

public class StartJobViewModel extends AndroidViewModel {
    public StartJobViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<CommonResponse> startJob(Context context, StartJobRequestModel startJobRequestModel) {
        return StartJobRepository.getInstance().startJob(context, startJobRequestModel);
    }
}
