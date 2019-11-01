package com.midwestpilotcars.callbacks

import com.midwestpilotcars.models.allDefaultModels.AllDefaultDataModel
import com.midwestpilotcars.models.loginModels.LoginResponseModel

interface DefaultDataCallBack{
    fun onDefaultCallbackSuccess(allDefaultDataModel: AllDefaultDataModel)
    fun onDefaultCallbackFailure(exception: Throwable)
    fun onAuthFailure()
}