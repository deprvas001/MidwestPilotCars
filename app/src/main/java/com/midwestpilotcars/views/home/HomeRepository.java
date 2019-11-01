package com.midwestpilotcars.views.home;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.midwestpilotcars.callbacks.CreateJobCallback;
import com.midwestpilotcars.callbacks.GetAllJobsCallback;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.createJob.AddJobRequestModel;
import com.midwestpilotcars.models.createJob.AddJobResponseModel;
import com.midwestpilotcars.models.getAllJobs.GetAllJobsModel;
import com.watcho.network.ApiUtils;

import org.jetbrains.annotations.NotNull;

public class HomeRepository {
    private static HomeRepository homeRepository = null;

    private HomeRepository() {

    }

    public static HomeRepository getInstance() {
        if (homeRepository == null)
            homeRepository = new HomeRepository();
        return homeRepository;
    }

    public MutableLiveData<CommonResponse> getAllJobs(Context context, String userType, String userId, String jobType, int offset, int pageSize) {
        MutableLiveData<CommonResponse> commonResponseMutableLiveData = new MutableLiveData<>();
        CommonResponse commonResponse = new CommonResponse();
        new ApiUtils().getAllJobs(SharedPreferenceHelper.Companion.getInstance().getUserData(context).getData().getAuthenticateToken(), userType, userId, jobType, offset, pageSize, new GetAllJobsCallback() {
            @Override
            public void onAuthFailure() {
                commonResponse.setStatus(false);
                commonResponse.setData(null);
                commonResponseMutableLiveData.postValue(commonResponse);
            }

            @Override
            public void onGetJobsFailure(@NotNull Throwable exception) {
                commonResponse.setStatus(false);
                commonResponse.setData(exception);
                commonResponseMutableLiveData.postValue(commonResponse);
            }

            @Override
            public void onGetJobsSuccess(@NotNull GetAllJobsModel getAllJobsModel) {
                commonResponse.setStatus(true);
                commonResponse.setData(getAllJobsModel);
                commonResponseMutableLiveData.postValue(commonResponse);
            }
        });
        return commonResponseMutableLiveData;
    }
}
