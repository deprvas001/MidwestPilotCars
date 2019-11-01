package com.midwestpilotcars.callbacks

import com.midwestpilotcars.models.createJob.AddJobResponseModel

interface CreateJobCallback{
    fun onCreateJobSuccess(createJobResponseModel: AddJobResponseModel)
    fun onCreateJobFailure(exception: Throwable)
    fun onAuthFailure()
}