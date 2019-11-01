package com.midwestpilotcars.views.startJob;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.midwestpilotcars.callbacks.StartJobCallBack;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.CommonResponse;
import com.midwestpilotcars.models.startJob.StartJobRequestModel;
import com.midwestpilotcars.models.startJob.StartJobResponseModel;
import com.watcho.network.ApiUtils;

public class StartJobRepository {
    private static StartJobRepository startJobRepository = null;

    private StartJobRepository() {

    }

    public static StartJobRepository getInstance() {
        if (startJobRepository == null)
            startJobRepository = new StartJobRepository();
        return startJobRepository;
    }

    public MutableLiveData<CommonResponse> startJob(Context context, StartJobRequestModel startJobRequestModel) {
        MutableLiveData<CommonResponse> commonResponseMutableLiveData = new MutableLiveData<>();
        CommonResponse commonResponse = new CommonResponse();
        ApiUtils apiUtils = new ApiUtils();
        apiUtils.startJob(SharedPreferenceHelper.Companion.getInstance().getUserData(context).getData().getAuthenticateToken(), startJobRequestModel, new StartJobCallBack() {
            @Override
            public void startJobSuccess(StartJobResponseModel startJobResponseModel) {
                commonResponse.setStatus(true);
                commonResponse.setData(startJobResponseModel);
                commonResponseMutableLiveData.postValue(commonResponse);
            }

            @Override
            public void startJobFailure(Throwable ex) {
                commonResponse.setStatus(false);
                commonResponse.setData(ex);
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
