package com.midwestpilotcars.network


import com.midwestpilotcars.helpers.Utils
import com.midwestpilotcars.models.allDefaultModels.AllDefaultDataModel
import com.midwestpilotcars.models.createJob.AddJobRequestModel
import com.midwestpilotcars.models.createJob.AddJobResponseModel
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordRequestModel
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordResponseModel
import com.midwestpilotcars.models.getAllJobs.GetAllJobsModel
import com.midwestpilotcars.models.loginModels.LoginRequestModel
import com.midwestpilotcars.models.loginModels.LoginResponseModel
import com.midwestpilotcars.models.resetPassword.ResetPasswordRequestModel
import com.midwestpilotcars.models.resetPassword.ResetPasswordResponseModel
import com.midwestpilotcars.models.startJob.StartJobRequestModel
import com.midwestpilotcars.models.startJob.StartJobResponseModel
import com.watcho.network.ApiService
import io.reactivex.Observable
import javax.inject.Inject

class NetworkRepository @Inject
constructor(private val apiService: ApiService) {

    fun loginUser(loginRequestModel: LoginRequestModel): Observable<LoginResponseModel> {
        return apiService.loginUser(Utils.getDeviceId(), loginRequestModel)
    }

    fun getDefaultData(authenticateToken: String): Observable<AllDefaultDataModel> {
        return apiService.getDefaultData(authenticateToken)
    }

    fun createJob(authenticateToken: String, addJobRequestModel: AddJobRequestModel): Observable<AddJobResponseModel> {
        return apiService.createJob(authenticateToken, addJobRequestModel)
    }

    fun getAllJobs(authenticateToken: String, userType: String, userId: String, jobType: String, offset: Int, perPageSize: Int): Observable<GetAllJobsModel> {
        return apiService.getAllJobs(authenticateToken, userType, userId, jobType, offset, perPageSize)
    }

    fun recoverPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel): Observable<ForgotPasswordResponseModel> {
        return apiService.recoverPassword(forgotPasswordRequestModel)
    }

    fun resetPassword(authenticateToken: String, resetPasswordRequestModel: ResetPasswordRequestModel): Observable<ResetPasswordResponseModel> {
        return apiService.resetPassword(authenticateToken, resetPasswordRequestModel)

    }

    fun startJob(authenticateToken: String, startJobRequestModel: StartJobRequestModel): Observable<StartJobResponseModel> {
        return apiService.startJob(authenticateToken, startJobRequestModel)

    }
}
