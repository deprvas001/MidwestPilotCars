package com.midwestpilotcars.views.createJob;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.createJob.AddJobRequestModel;

public class AddJobViewModel extends AndroidViewModel {
    public AddJobViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CommonResponse> createJob(Context context, AddJobRequestModel addJobRequestModel) {
        return AddJobRepository.getInstance().createJob(context, addJobRequestModel);

    }
}
