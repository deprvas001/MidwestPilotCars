package com.watcho.network

import android.annotation.SuppressLint
import com.midwestpilotcars.MidwestPilotCars
import com.midwestpilotcars.callbacks.*
import com.midwestpilotcars.models.loginModels.LoginRequestModel
import com.midwestpilotcars.network.NetworkRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton
import com.midwestpilotcars.models.createJob.AddJobRequestModel
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordRequestModel
import com.midwestpilotcars.models.resetPassword.ResetPasswordRequestModel
import com.midwestpilotcars.models.startJob.StartJobRequestModel


@Singleton
class ApiUtils {
    @Inject
    lateinit var networkRepository: NetworkRepository

    init {
        MidwestPilotCars.getInstance().daggerComponent!!.inject(this)
    }

    @SuppressLint("CheckResult")
    fun loginUser(userName: String, password: String, loginCallback: LoginCallback) {
        val loginRequestModel = LoginRequestModel()
        loginRequestModel.userName = userName
        loginRequestModel.userPassword = password
        networkRepository.loginUser(loginRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    loginCallback.onLoginSuccess(it)
                }, { error ->
                    loginCallback.onLoginFailure(error)
                })
    }

    @SuppressLint("CheckResult")
    fun getDefaultData(authenticateToken: String, defaultDataCallBack: DefaultDataCallBack) {
        networkRepository.getDefaultData(authenticateToken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    defaultDataCallBack.onDefaultCallbackSuccess(it)
                }, { error ->
                    if (error.message.equals("HTTP 401 Unauthorized", true)) {
                        defaultDataCallBack.onAuthFailure()
                    } else {
                        defaultDataCallBack.onDefaultCallbackFailure(error)
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun createNewJob(authenticateToken: String, createJobResponseModel: AddJobRequestModel, createJobCallback: CreateJobCallback) {
        networkRepository.createJob(authenticateToken, createJobResponseModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.status == 1L) {
                        createJobCallback.onCreateJobSuccess(it)
                    }
                }, { error ->
                    if (error.message.equals("HTTP 401 Unauthorized", true)) {
                        createJobCallback.onAuthFailure()
                    } else {
                        createJobCallback.onCreateJobFailure(error)
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun getAllJobs(authenticateToken: String, userType: String, userId: String, jobType: String, offset: Int, perPageSize: Int, getAllJobsCallback: GetAllJobsCallback) {
        networkRepository.getAllJobs(authenticateToken, userType, userId, jobType, offset, perPageSize).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    getAllJobsCallback.onGetJobsSuccess(it)
                }, { error ->
                    if (error.message.equals("HTTP 401 Unauthorized", true)) {
                        getAllJobsCallback.onAuthFailure()
                    } else {
                        getAllJobsCallback.onGetJobsFailure(error)
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun recoverPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel, forgotPasswordCallback: ForgotPasswordCallbacks) {
        networkRepository.recoverPassword(forgotPasswordRequestModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    forgotPasswordCallback.passwordRecoverSuccess(it)
                }, { error ->
                    forgotPasswordCallback.passwordRecoverFailure(error)
                })
    }

    @SuppressLint("CheckResult")
    fun resetPassword(authenticateToken: String, resetPasswordRequestModel: ResetPasswordRequestModel, resetPasswordCallBack: ResetPasswordCallBack) {
        networkRepository.resetPassword(authenticateToken, resetPasswordRequestModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    resetPasswordCallBack.resetPasswordSuccess(it)
                }, { error ->
                    if (error.message.equals("HTTP 401 Unauthorized", true)) {
                        resetPasswordCallBack.onAuthFailure()
                    } else {
                        resetPasswordCallBack.resetPasswordFailure(error)
                    }
                })
    }

    @SuppressLint("CheckResult")
    fun startJob(authenticateToken: String, startJobRequestModel: StartJobRequestModel, startJobCallBack: StartJobCallBack) {
        networkRepository.startJob(authenticateToken, startJobRequestModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    startJobCallBack.startJobSuccess(it)
                }, { error ->
                    if (error.message.equals("HTTP 401 Unauthorized", true)) {
                        startJobCallBack.onAuthFailure()
                    } else {
                        startJobCallBack.startJobFailure(error)
                    }
                })
    }
}