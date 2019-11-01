package com.midwestpilotcars.apiInterface;

import com.google.android.gms.common.api.Api;
import com.midwestpilotcars.constants.ApiConstants;
import com.midwestpilotcars.models.StatusAddDayExpense;
import com.midwestpilotcars.models.addDayExpenses.DayDetails;
import com.midwestpilotcars.models.addDayExpenses.DayExpenseData;
import com.midwestpilotcars.models.completejob.CompleteJob;
import com.midwestpilotcars.models.completejob.StatusCompleteJob;
import com.midwestpilotcars.models.getAllJobs.GetAllJobsModel;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.models.moneyRequest.MoneyRequestDetail;
import com.midwestpilotcars.models.wallet.ApproxAmountPay;
import com.midwestpilotcars.models.wallet.WalletAllDetail;
import com.midwestpilotcars.models.wallet.WalletModel;
import com.watcho.network.ApiService;
import com.watcho.network.ApiUtils;

import java.sql.Array;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CompleteJobApi {
    String BASE_URL = ApiConstants.BASE_URL;
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getJobDoneDetails")
    Call<StatusCompleteJob> getCompleteJob(@Query("job_id") String id, @Header("AuthenticateToken") String authHeader);

    @FormUrlEncoded
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("dayexpense")
    Call<StatusAddDayExpense> setDayExpense(@Header("AuthenticateToken") String authHeader,@Field("gas_expense_mode") String gas_expense_mode,
                                            @Field("no_of_miles_travelled_comment") String miles_travell_comment,@Field("motel_expense_comment") String motel_expense_comment,
                                            @Field("no_go_day") String goes_day,@Field("no_of_down_time_hours") String down_time,@Field("gas_expense_price") String gas_expense_price,
                                            @Field("job_id") String job_id,@Field("motel_expense_price") String motel_expense_price,@Field("motel_expense_image") String motel_expense_image
  ,@Field("no_of_down_time_hours_comment") String down_time_comment,@Field("gas_expense_image") String gas_expense_image,@Field("motel_expense_mode") String motel_expense_mode,
                                            @Field("gas_expense_comment") String gas_expense_comment,@Field("choose_costing_from") String choose_costing_from,
                                            @Field("other_expense") String other_expense,@Field("no_of_miles_travelled") String no_of_miles_travelled

                                            );


   /* @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("dayexpense")
    Call<StatusAddDayExpense> setDayExpense(@Header("AuthenticateToken") String authHeader,@Body String body
    );*/

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getDayExpenseData")
    Call<DayExpenseData> getDayExpenseBy(@Query("job_id") String id,@Query("type") String type, @Header("AuthenticateToken") String authHeader);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getDayExpenseData")
    Call<DayDetails> getDayDetails(@Query("job_id") String id, @Query("day_expense_id") String type, @Header("AuthenticateToken") String authHeader);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getAllJobs")
    Call<GetAllJobsModel> getOngoing(@Query("user_type") String id, @Query("user_id") String type, @Query("job_type") String job_type,
                                     @Query("offset") String offset, @Query("per_page") String per_page, @Header("AuthenticateToken") String authHeader);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getWalletData")
    Call<WalletModel> getDriverWallet(@Header("AuthenticateToken") String authHeader);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getApproxEarning")
    Call<ApproxAmountPay> getApproxAmount(@Header("AuthenticateToken") String authHeader, @Query("job_id") String job_id);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("getWalletRequest")
    Call<WalletAllDetail> getAllWallet(@Header("AuthenticateToken") String authHeader,@Query("type") String type);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("postWalletAmount")
    Call<MoneyRequestDetail> requestedMoney(@Header("AuthenticateToken") String authHeader, @Body Map<String,String>  amount);
}
