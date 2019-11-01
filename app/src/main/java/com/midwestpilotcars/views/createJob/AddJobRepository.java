package com.midwestpilotcars.views.createJob;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.midwestpilotcars.callbacks.CreateJobCallback;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.createJob.AddJobRequestModel;
import com.midwestpilotcars.models.createJob.AddJobResponseModel;
import com.watcho.network.ApiUtils;

import org.jetbrains.annotations.NotNull;

public class AddJobRepository {
    private static AddJobRepository createJobRepository = null;

    private AddJobRepository() {

    }

    public static AddJobRepository getInstance() {
        if (createJobRepository == null)
            createJobRepository = new AddJobRepository();
        return createJobRepository;
    }

    public MutableLiveData<CommonResponse> createJob(Context context, AddJobRequestModel addJobRequestModel) {
        MutableLiveData<CommonResponse> commonResponseMutableLiveData = new MutableLiveData<>();
        CommonResponse commonResponse = new CommonResponse();
        new ApiUtils().createNewJob(SharedPreferenceHelper.Companion.getInstance().getUserData(context).getData().getAuthenticateToken(), addJobRequestModel, new CreateJobCallback() {
            @Override
            public void onCreateJobSuccess(@NotNull AddJobResponseModel createJobResponseModel) {
                commonResponse.setStatus(true);
                commonResponse.setData(createJobResponseModel);
                commonResponseMutableLiveData.postValue(commonResponse);
            }

            @Override
            public void onCreateJobFailure(@NotNull Throwable exception) {
                commonResponse.setStatus(false);
                commonResponse.setData(exception);
                commonResponseMutableLiveData.postValue(commonResponse);
            }

            @Override
            public void onAuthFailure() {
                commonResponse.setStatus(false);
                commonResponse.setData(null);
                commonResponseMutableLiveData.postValue(commonResponse);
            }
        });
        return commonResponseMutableLiveData;
    }
}
