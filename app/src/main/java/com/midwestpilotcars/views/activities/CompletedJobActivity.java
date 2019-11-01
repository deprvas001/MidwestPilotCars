package com.midwestpilotcars.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.midwestpilotcars.R;
import com.midwestpilotcars.apiInterface.CompleteJobApi;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.helpers.Utils;
import com.midwestpilotcars.models.completejob.CompleteJob;
import com.midwestpilotcars.models.completejob.StatusCompleteJob;
import com.midwestpilotcars.views.splash.SplashActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompletedJobActivity extends BaseActivity {
    String job_id = "";
    TextView driver_title, driver_name;
    View view;
    LinearLayout view_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_job);
        getSupportActionBar().setTitle(getString(R.string.title_complete_job));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view_layout = (LinearLayout) findViewById(R.id.complete_view_layout);
        view_layout.setVisibility(View.GONE);
        //   initializeView();

        job_id = getIntent().getExtras().getString(AppConstants.JOB_KEY);
        getCompletJob();
    }

    private void getCompletJob() {
        showProgressDialog(getString(R.string.loading));
        String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CompleteJobApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CompleteJobApi api = retrofit.create(CompleteJobApi.class);

        Call<StatusCompleteJob> call = api.getCompleteJob(job_id, token);

        call.enqueue(new Callback<StatusCompleteJob>() {
            @Override
            public void onResponse(Call<StatusCompleteJob> call, Response<StatusCompleteJob> response) {
                StatusCompleteJob completeJob = response.body();


                if (completeJob.getStatus().equals("1")) {
                    if (completeJob.getMessage().equals(getResources().getString(R.string.invoice_pending))) {
                        hideProgressDialog();
                        showMessage(completeJob.getMessage());
                    } else if (completeJob.getMessage().equals(getResources().getString(R.string.job_complete_done))) {
                        initializeView(completeJob);
                    }
                }


            }

            @Override
            public void onFailure(Call<StatusCompleteJob> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(CompletedJobActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        new Handler().postDelayed(() -> {
            //Companion is final static class in Utils class check after decompile its kotlin
            finish();
        }, 4000);
    }

    public void initializeView(StatusCompleteJob completeJob) {
        try{

        view = (View) findViewById(R.id.driver_name_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Driver Name");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_truck_driver_name() !=null)
        driver_name.setText(completeJob.getData().getJob_truck_driver_name());

        view = (View) findViewById(R.id.trucking_company_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Truck Company Name");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTrucking_company_name()!=null)
        driver_name.setText(completeJob.getData().getTrucking_company_name());

        view = (View) findViewById(R.id.load_no_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Load Number");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_load_number() !=null)
        driver_name.setText(completeJob.getData().getJob_load_number());

        view = (View) findViewById(R.id.truck_no_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Truck Number");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_truck_number()!=null)
        driver_name.setText(completeJob.getData().getJob_truck_number());

        view = (View) findViewById(R.id.load_description_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Load Description");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_load_description()!=null)
        driver_name.setText(completeJob.getData().getJob_load_description());

        view = (View) findViewById(R.id.load_type_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Load Type");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_truck_load_type_id()!=null)
        driver_name.setText(completeJob.getData().getJob_truck_load_type_id());

        view = (View) findViewById(R.id.load_type_name_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Load Type Name");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getLoad_type_name()!=null)
        driver_name.setText(completeJob.getData().getLoad_type_name());


        view = (View) findViewById(R.id.load_dot_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Load Dot Number");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_load_number()!=null)
        driver_name.setText(completeJob.getData().getJob_load_number());


        view = (View) findViewById(R.id.load_driver_no_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Driver Number");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_truck_driver_phone_number()!=null)
        driver_name.setText(completeJob.getData().getJob_truck_driver_phone_number());

        view = (View) findViewById(R.id.load_truck_width_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Truck Width");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_truck_width()!=null)
        driver_name.setText(completeJob.getData().getJob_truck_width());

        view = (View) findViewById(R.id.load_truck_height_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Truck Height");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_truck_height()!=null)
        driver_name.setText(completeJob.getData().getJob_truck_height());

        view = (View) findViewById(R.id.job_start_date_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Start Date");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_start_date()!=null)
        driver_name.setText(completeJob.getData().getJob_start_date());

        view = (View) findViewById(R.id.job_starting_point_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Starting Point");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_starting_point()!=null)
        driver_name.setText(completeJob.getData().getJob_starting_point());

        view = (View) findViewById(R.id.job_ending_point_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Ending Point");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_ending_point()!=null)
        driver_name.setText(completeJob.getData().getJob_ending_point());

        view = (View) findViewById(R.id.total_gas_expense_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Gas Expense");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_gas_expense()!=null)
        driver_name.setText(completeJob.getData().getTotal_gas_expense());

        view = (View) findViewById(R.id.total_gas_expense_deduction_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Gas Expense Deduction");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_gas_expense_deduction()!=null)
        driver_name.setText(completeJob.getData().getTotal_gas_expense_deduction());

        view = (View) findViewById(R.id.new_total_gas_expense_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("New Total Gas Expense");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getNew_total_gas_expense()!=null)
        driver_name.setText(completeJob.getData().getNew_total_gas_expense());

        view = (View) findViewById(R.id.total_motel_expense_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Motel Expense");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_motel_expense()!=null)
        driver_name.setText(completeJob.getData().getTotal_motel_expense());

        view = (View) findViewById(R.id.total_motel_expense_deduction_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Motel Expense Deduction");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_motel_expense_deduction()!=null)
        driver_name.setText(completeJob.getData().getTotal_motel_expense_deduction());

        view = (View) findViewById(R.id.new_toal_motel_expense_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("New Total Motel Expense ");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getNew_toal_motel_expense()!=null)
        driver_name.setText(completeJob.getData().getNew_toal_motel_expense());

        view = (View) findViewById(R.id.total_other_expense_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Other Expense");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_other_expense()!=null)
        driver_name.setText(completeJob.getData().getTotal_other_expense());

        view = (View) findViewById(R.id.total_other_expense_deduction_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Other Expense Deduction");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_other_expense_deduction()!=null)
        driver_name.setText(completeJob.getData().getTotal_other_expense_deduction());

        view = (View) findViewById(R.id.new_toal_other_expense_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("New Total Other Expense");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getNew_toal_other_expense()!=null)
        driver_name.setText(completeJob.getData().getNew_toal_other_expense());

        view = (View) findViewById(R.id.total_day_rate_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Day Rate");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_day_rate()!=null)
        driver_name.setText(completeJob.getData().getTotal_day_rate());

        view = (View) findViewById(R.id.total_no_goes_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total No Goes");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_no_goes()!=null)
        driver_name.setText(completeJob.getData().getTotal_no_goes());


        view = (View) findViewById(R.id.total_price_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Price");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_price()!=null)
        driver_name.setText(completeJob.getData().getTotal_price());

        view = (View) findViewById(R.id.total_down_time_price_per_hour_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Down Time Price Per Hour");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_down_time_price_per_hour()!=null)
        driver_name.setText(completeJob.getData().getTotal_down_time_price_per_hour());

        view = (View) findViewById(R.id.total_down_time_hours_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Down Time Hour");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_down_time_hours()!=null)
        driver_name.setText(completeJob.getData().getTotal_down_time_hours());

        view = (View) findViewById(R.id.total_down_time_hours_price_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Down Time Hour Price");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_down_time_hours_price()!=null)
        driver_name.setText(completeJob.getData().getTotal_down_time_hours_price());

        view = (View) findViewById(R.id.total_miles_travelled_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Miles Travelled");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_miles_travelled()!=null)
        driver_name.setText(completeJob.getData().getTotal_miles_travelled());

        view = (View) findViewById(R.id.total_card_deduction_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Card Deduction");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_card_deduction()!=null)
        driver_name.setText(completeJob.getData().getTotal_card_deduction());

        view = (View) findViewById(R.id.total_pay_to_driver_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Total Pay To Driver ");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTotal_pay_to_driver()!=null)
        driver_name.setText(completeJob.getData().getTotal_pay_to_driver());

        view = (View) findViewById(R.id.cash_in_advance_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Cash In Advance ");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getCash_in_advance()!=null)
        driver_name.setText(completeJob.getData().getCash_in_advance());

        view = (View) findViewById(R.id.job_pay_per_mile_cost_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Job Pay Per Mile Cost");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_pay_per_mile_cost()!=null)
        driver_name.setText(completeJob.getData().getJob_pay_per_mile_cost());

        view = (View) findViewById(R.id.job_per_day_cost_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Job Per Day Cost");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_per_day_cost()!=null)
        driver_name.setText(completeJob.getData().getJob_per_day_cost());

        view = (View) findViewById(R.id.job_no_goes_price_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Job No Goes Price");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_no_goes_price()!=null)
        driver_name.setText(completeJob.getData().getJob_no_goes_price());

        view = (View) findViewById(R.id.job_detention_price_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Job Detention Price");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_detention_price()!=null)
        driver_name.setText(completeJob.getData().getJob_detention_price());

        view = (View) findViewById(R.id.end_trip_status_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("End Trip Status");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getEnd_trip_status()!=null)
        driver_name.setText(completeJob.getData().getEnd_trip_status());

        view = (View) findViewById(R.id.days_filled_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Day Filled");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getDays_filled()!=null)
        driver_name.setText(completeJob.getData().getDays_filled());

        view = (View) findViewById(R.id.job_comments_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Job Comment");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_comments()!=null)
        driver_name.setText(completeJob.getData().getJob_comments());

        view = (View) findViewById(R.id.trucking_company_id_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Trucking Company Id");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTrucking_company_id()!=null)
        driver_name.setText(completeJob.getData().getTrucking_company_id());

        view = (View) findViewById(R.id.trucking_company_rep_name_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Trucking Company Rep Name");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getTrucking_company_rep_name()!=null)
        driver_name.setText(completeJob.getData().getTrucking_company_rep_name());

        view = (View) findViewById(R.id.job_truck_load_type_id_layout);
        driver_title = (TextView) view.findViewById(R.id.title);
        driver_title.setText("Trucking Load Type");
        driver_name = (TextView) view.findViewById(R.id.value);
        if(completeJob.getData().getJob_truck_load_type_id()!=null)
        driver_name.setText(completeJob.getData().getJob_truck_load_type_id());
        hideProgressDialog();
        view_layout.setVisibility(View.VISIBLE);

        }catch (NullPointerException e){
            hideProgressDialog();
            Toast.makeText(this, "Try later", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            finish();

        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
