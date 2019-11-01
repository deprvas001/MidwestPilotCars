package com.midwestpilotcars.callbacks;

import com.midwestpilotcars.models.startJob.StartJobResponseModel;

public interface StartJobCallBack {
    void startJobSuccess(StartJobResponseModel startJobResponseModel);

    void startJobFailure(Throwable ex);

    void onAuthFailure();
}
