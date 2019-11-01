package com.midwestpilotcars.callbacks

import com.midwestpilotcars.models.getAllJobs.GetAllJobsModel

interface GetAllJobsCallback{
    fun onGetJobsSuccess(getAllJobsModel: GetAllJobsModel)
    fun onGetJobsFailure(exception: Throwable)
    fun onAuthFailure()
}