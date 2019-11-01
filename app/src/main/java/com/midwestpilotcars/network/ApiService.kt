package com.watcho.network


import com.midwestpilotcars.constants.ApiConstants
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
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @Headers(ApiConstants.KEY_SOURCES + ":" + ApiConstants.SOURCES,
            ApiConstants.KEY_USER_TYPE + ":" + ApiConstants.USER_TYPE,
            ApiConstants.KEY_DEVICE_TYPE + ":" + ApiConstants.DEVICE_TYPE)
    @POST("userLogin")
    fun loginUser(@Header(ApiConstants.KEY_DEVICE_IDE) deviceId: String, @Body loginRequestModel: LoginRequestModel): Observable<LoginResponseModel>

    @GET("getAllByDefaultData?type=ALL")
    fun getDefaultData(@Header(ApiConstants.KEY_AUTH_TOKEN) authToken: String): Observable<AllDefaultDataModel>

    @POST("postJobs")
    fun createJob(@Header(ApiConstants.KEY_AUTH_TOKEN) authenticateToken: String, @Body addJobRequestModel: AddJobRequestModel): Observable<AddJobResponseModel>

    @GET("getAllJobs")
    fun getAllJobs(@Header(ApiConstants.KEY_AUTH_TOKEN) authenticateToken: String, @Query("user_type") userType: String, @Query("user_id") userId: String, @Query("job_type") jobType: String, @Query("offset") offset: Int, @Query("per_page") perPageSize: Int): Observable<GetAllJobsModel>

    @POST("forgetPassword")
    fun recoverPassword(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): Observable<ForgotPasswordResponseModel>

    @POST("changePassword")
    fun resetPassword(@Header(ApiConstants.KEY_AUTH_TOKEN) authenticateToken: String, @Body resetPasswordRequestModel: ResetPasswordRequestModel): Observable<ResetPasswordResponseModel>

    @POST("startJob")
    fun startJob(@Header(ApiConstants.KEY_AUTH_TOKEN) authenticateToken: String, @Body startJobRequestModel: StartJobRequestModel): Observable<StartJobResponseModel>
}
