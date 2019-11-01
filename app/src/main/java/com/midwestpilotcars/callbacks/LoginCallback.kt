package com.midwestpilotcars.callbacks

import com.midwestpilotcars.models.loginModels.LoginResponseModel

interface LoginCallback{
    fun onLoginSuccess(loginResponseModel: LoginResponseModel)
    fun onLoginFailure(exception: Throwable)
}