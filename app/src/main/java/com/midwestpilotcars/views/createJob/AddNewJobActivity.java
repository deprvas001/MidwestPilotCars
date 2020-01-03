package com.midwestpilotcars.views.createJob;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midwestpilotcars.R;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.callbacks.CreateJobCallback;
import com.midwestpilotcars.callbacks.OnDefaultDataItemClicked;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.databinding.ActivityAddNewJobBinding;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.createJob.AddJobRequestModel;
import com.midwestpilotcars.models.allDefaultModels.LOADTYPE;
import com.midwestpilotcars.models.allDefaultModels.TRUCKINGCOMPANY;
import com.midwestpilotcars.models.createJob.AddJobResponseModel;
import com.midwestpilotcars.views.activities.ExpenseDayAdd2;
import com.midwestpilotcars.views.activities.MapActivity;
import com.midwestpilotcars.views.home.MainActivity;
import com.midwestpilotcars.views.login.LoginActivity;
import com.watcho.network.ApiUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddNewJobActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, OnDefaultDataItemClicked, View.OnFocusChangeListener {
    ActivityAddNewJobBinding activityAddNewJobBinding;
    private AddJobRequestModel addJobRequestModel;
    private AddJobViewModel addJobViewModel;
    private boolean isAllFill = true;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activityAddNewJobBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_job);
        addJobViewModel = ViewModelProviders.of(this).get(AddJobViewModel.class);
        setUpModelandView();
    }

    private void setUpModelandView() {
        addJobRequestModel = new AddJobRequestModel();
        activityAddNewJobBinding.setAddJobRequestModel(addJobRequestModel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_new_job));
        activityAddNewJobBinding.imgBtnAddJob.setOnClickListener(this);
        activityAddNewJobBinding.etSelectDate.setOnFocusChangeListener(this);
        activityAddNewJobBinding.etSelectDate.setOnClickListener(this);
        activityAddNewJobBinding.etLoadType.setOnFocusChangeListener(this);
        activityAddNewJobBinding.etLoadType.setOnClickListener(this);
        activityAddNewJobBinding.etTruckingCompany.setOnFocusChangeListener(this);
        activityAddNewJobBinding.etTruckingCompany.setOnClickListener(this);
        activityAddNewJobBinding.etStartLocation.setOnFocusChangeListener(this);
        activityAddNewJobBinding.etStartLocation.setOnClickListener(this);
        activityAddNewJobBinding.etEndLocation.setOnFocusChangeListener(this);
        activityAddNewJobBinding.etEndLocation.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                alertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_add_job:
                createJob();
                break;

            case R.id.et_select_date:
                selectDate();
                break;
            case R.id.et_load_type:
                selectLoadType();
                break;
            case R.id.et_trucking_company:
                selectTruckCompany();
                break;
            case R.id.et_start_location:
                getLocation(AppConstants.START_LOCATION_REQUEST_CODE);
                break;
            case R.id.et_end_location:
                getLocation(AppConstants.END_LOCATION_REQUEST_CODE);
                break;
        }
    }

    private void getLocation(int requestCode) {
        startActivityForResult(new Intent(this, MapActivity.class), requestCode);
    }

    private void selectTruckCompany() {
        DialogUtils.showDefaultDataDialog(this, AppConstants.TRUCK_COMPANY);

    }

    private void selectLoadType() {
        DialogUtils.showDefaultDataDialog(this, AppConstants.LOAD_TYPE);
    }

    private void selectDate() {
        DialogUtils.getCurrentDate(this);
    }

    private void createJob() {
         isAllFill = true;

        if( TextUtils.isEmpty(activityAddNewJobBinding.etSelectDate.getText())){
            /**
             *   You can Toast a message here that the Username is Empty
             **/
            activityAddNewJobBinding.selectDateView.setVisibility(View.VISIBLE);
            /*activityAddNewJobBinding.etSelectDate.setError( "Please select the date" );*/
            isAllFill = false;
        }else{
            activityAddNewJobBinding.selectDateView.setVisibility(View.GONE);
            /*activityAddNewJobBinding.etSelectDate.setError( "Please select the date" );*/
            isAllFill = true;
        }

        if(TextUtils.isEmpty(activityAddNewJobBinding.etSelectDate.getText())){
            activityAddNewJobBinding.loadTypeView.setVisibility(View.VISIBLE);
         //   activityAddNewJobBinding.etLoadType.setError("Please select load type");
            isAllFill = false;
        }else{
            activityAddNewJobBinding.loadTypeView.setVisibility(View.GONE);
            //   activityAddNewJobBinding.etLoadType.setError("Please select load type");
            isAllFill = true;
        }
        if(TextUtils.isEmpty(activityAddNewJobBinding.etTruckingCompany.getText())){
            activityAddNewJobBinding.truckCompanyView.setVisibility(View.VISIBLE);
            //activityAddNewJobBinding.etTruckingCompany.setError("Please select trucking company");
            isAllFill = false;
        }else{
            activityAddNewJobBinding.truckCompanyView.setVisibility(View.GONE);
            //activityAddNewJobBinding.etTruckingCompany.setError("Please select trucking company");
            isAllFill = true;
        }
        if(TextUtils.isEmpty(activityAddNewJobBinding.etStartLocation.getText())){
            activityAddNewJobBinding.startLocationView.setVisibility(View.VISIBLE);
          //  activityAddNewJobBinding.etStartLocation.setError("Please select start location");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.startLocationView.setVisibility(View.GONE);
            //  activityAddNewJobBinding.etStartLocation.setError("Please select start location");
            isAllFill = true;
        }

        if(TextUtils.isEmpty(activityAddNewJobBinding.etEndLocation.getText())){
             activityAddNewJobBinding.endLocationView.setVisibility(View.VISIBLE);
         //   activityAddNewJobBinding.etEndLocation.setError("Please select end location");
            isAllFill = false;
        }else{
            activityAddNewJobBinding.endLocationView.setVisibility(View.GONE);
            //   activityAddNewJobBinding.etEndLocation.setError("Please select end location");
            isAllFill = true;
        }

       if(TextUtils.isEmpty(activityAddNewJobBinding.etDriverName.getText())) {
           activityAddNewJobBinding.driverNameView.setVisibility(View.VISIBLE);
          // activityAddNewJobBinding.etDriverName.setError("Please enter driver name");
           isAllFill = false;
       }else{
           activityAddNewJobBinding.driverNameView.setVisibility(View.GONE);
           // activityAddNewJobBinding.etDriverName.setError("Please enter driver name");
           isAllFill = true;
       }

        if(TextUtils.isEmpty(activityAddNewJobBinding.etLoadNo.getText())) {
            activityAddNewJobBinding.loadNoView.setVisibility(View.VISIBLE);
          //  activityAddNewJobBinding.etLoadNo.setError("Please enter load number");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.loadNoView.setVisibility(View.GONE);
            //  activityAddNewJobBinding.etLoadNo.setError("Please enter load number");
            isAllFill = true;
        }

        if(TextUtils.isEmpty(activityAddNewJobBinding.etDotNo.getText())) {
            activityAddNewJobBinding.dotNoView.setVisibility(View.VISIBLE);
       //     activityAddNewJobBinding.etDotNo.setError("Please enter dot no");
            isAllFill = false;

        }else{
            activityAddNewJobBinding.dotNoView.setVisibility(View.GONE);
            //     activityAddNewJobBinding.etDotNo.setError("Please enter dot no");
            isAllFill = true;
        }
        if(TextUtils.isEmpty(activityAddNewJobBinding.etTruckNo.getText())) {
            activityAddNewJobBinding.truckNoView.setVisibility(View.VISIBLE);
        //    activityAddNewJobBinding.etTruckNo.setError("Please enter truck number");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.truckNoView.setVisibility(View.GONE);
            //    activityAddNewJobBinding.etTruckNo.setError("Please enter truck number");
            isAllFill = true;
        }
        if(TextUtils.isEmpty(activityAddNewJobBinding.etTruckDriveNumber.getText())) {
            activityAddNewJobBinding.truckDriverView.setVisibility(View.VISIBLE);
        //    activityAddNewJobBinding.etTruckDriveNumber.setError("Please enter truck driver number");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.truckDriverView.setVisibility(View.GONE);
            //    activityAddNewJobBinding.etTruckDriveNumber.setError("Please enter truck driver number");
            isAllFill = true;
        }

       /* if(TextUtils.isEmpty(activityAddNewJobBinding.etTruckDriveNumber.getText())) {
            activityAddNewJobBinding.etTruckDriveNumber.setError("Please enter truck driver number");
            isAllFill = false;
        }*/

        if(TextUtils.isEmpty(activityAddNewJobBinding.etLoadDescription.getText())) {
            activityAddNewJobBinding.loadDescView.setVisibility(View.VISIBLE);
           // activityAddNewJobBinding.etLoadDescription.setError("Please enter load description");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.loadDescView.setVisibility(View.GONE);
            // activityAddNewJobBinding.etLoadDescription.setError("Please enter load description");
            isAllFill = true;
        }

        if(TextUtils.isEmpty(activityAddNewJobBinding.etCashAdvance.getText())) {
            activityAddNewJobBinding.cashAdvanceView.setVisibility(View.VISIBLE);
          //  activityAddNewJobBinding.etCashAdvance.setError("Please enter cash in advance");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.cashAdvanceView.setVisibility(View.GONE);
            //  activityAddNewJobBinding.etCashAdvance.setError("Please enter cash in advance");
            isAllFill = true;
        }

        if(TextUtils.isEmpty(activityAddNewJobBinding.etTruckWidth.getText())) {
            activityAddNewJobBinding.truckWidthView.setVisibility(View.VISIBLE);
          //  activityAddNewJobBinding.etTruckWidth.setError("Please enter truck width");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.truckWidthView.setVisibility(View.GONE);
            //  activityAddNewJobBinding.etTruckWidth.setError("Please enter truck width");
            isAllFill = true;
        }
        if(TextUtils.isEmpty(activityAddNewJobBinding.etTruckHeight.getText())) {
            activityAddNewJobBinding.truckHeightView.setVisibility(View.VISIBLE);
           // activityAddNewJobBinding.etTruckHeight.setError("Please enter truck height");
            isAllFill = false;
        }else {
            activityAddNewJobBinding.truckHeightView.setVisibility(View.GONE);
            // activityAddNewJobBinding.etTruckHeight.setError("Please enter truck height");
            isAllFill = true;
        }
        if(isAllFill){
            activityAddNewJobBinding.progressBar.setVisibility(View.VISIBLE);
            activityAddNewJobBinding.imgBtnAddJob.setClickable(false);

            sendRequest(addJobRequestModel);
        }else{
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "yyyy-MM-dd hh:mm:ss"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        activityAddNewJobBinding.etSelectDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onDefaultItemClicked(Parcelable defaultdata) {
        if (defaultdata instanceof LOADTYPE) {
            ((LOADTYPE) defaultdata).setLoadTypeDescription("");
            addJobRequestModel.setJobTruckLoadTypeId(((LOADTYPE) defaultdata).getLoadTypeId());
            addJobRequestModel.setJobLoadDescription(((LOADTYPE) defaultdata).getLoadTypeDescription());
            activityAddNewJobBinding.setLoadType((LOADTYPE) defaultdata);
        } else if (defaultdata instanceof TRUCKINGCOMPANY) {
            addJobRequestModel.setJobAssignTruckingCompanyId(((TRUCKINGCOMPANY) defaultdata).getTruckingCompanyId());
            activityAddNewJobBinding.setTruckingCompany((TRUCKINGCOMPANY) defaultdata);
        }
        DialogUtils.hideDefaultDataDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case AppConstants.START_LOCATION_REQUEST_CODE:
                    activityAddNewJobBinding.etStartLocation.setText(data.getStringExtra("Address"));
                    break;
                case AppConstants.END_LOCATION_REQUEST_CODE:
                    activityAddNewJobBinding.etEndLocation.setText(data.getStringExtra("Address"));
                    break;
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.et_select_date:
                    selectDate();
                    break;
                case R.id.et_load_type:
                    selectLoadType();
                    break;
                case R.id.et_trucking_company:
                    selectTruckCompany();
                    break;
                case R.id.et_start_location:
                    getLocation(AppConstants.START_LOCATION_REQUEST_CODE);
                    break;
                case R.id.et_end_location:
                    getLocation(AppConstants.END_LOCATION_REQUEST_CODE);
                    break;
            }
        }
    }

    public void goToLogin() {
        Toast.makeText(this, getString(R.string.auth_failure), Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }



        public void sendRequest(AddJobRequestModel addJobRequestModel) {
            try {
                showProgressDialog("Please wait...");
                String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
                String URL = "http://webfume.net/jasonapp/api/postJobs";
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                //   RequestQueue requestQueue = Volley.newRequestQueue(this);
                JSONObject params = new JSONObject();
                params.put("job_start_date", addJobRequestModel.getJobStartDate());
                params.put("job_starting_point",addJobRequestModel.getJobStartingPoint());
                params.put("job_ending_point",addJobRequestModel.getJobEndingPoint());
                params.put("job_cash_in_advance", addJobRequestModel.getJobCashInAdvance());
                params.put("job_assign_trucking_company_id", addJobRequestModel.getJobAssignTruckingCompanyId());
                params.put("job_truck_load_type_id", addJobRequestModel.getJobTruckLoadTypeId());
                params.put("job_load_number", addJobRequestModel.getJobLoadNumber());
                params.put("job_load_description", activityAddNewJobBinding.etLoadDescription.getText().toString());
                params.put("jab_dot_number", addJobRequestModel.getJobDotNumber());
                params.put("job_truck_driver_name", addJobRequestModel.getJobTruckDriverName());
                params.put("job_truck_driver_phone_number",addJobRequestModel.getJobTruckDriverPhoneNumber());
                params.put("job_truck_number", addJobRequestModel.getJobTruckNumber());
                params.put("job_truck_height", addJobRequestModel.getJobTruckHeight());
                params.put("job_truck_width",addJobRequestModel.getJobTruckWidth());
                params.put("job_truck_weight", activityAddNewJobBinding.etTruckWeight.getText().toString());
                params.put("job_truck_length",activityAddNewJobBinding.etTruckLength.getText().toString());



                final String mRequestBody = params.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            hideProgressDialog();
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if (status.equals("1")) {
                                if (message.equals("Job Successfully Submitted !")) {

                                    Toast.makeText(AddNewJobActivity.this, message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddNewJobActivity.this,MainActivity.class));
                                    finish();

                                } /*else if (message.equals("successfully your day job is done")) {
                                hideProgressDialog();

                                String end_trip_status = jsonObject.getString("end_trip_status");

                                intent = new Intent(ExpenseDayAdd2.this, EndForm.class);
                                intent.putExtra(AppConstants.END_STATUS, end_trip_status);
                                intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                intent.putExtra(AppConstants.JOB_KEY, job_id);
                                startActivity(intent);
                                finish();

                            }*/
                            } else if (status.equals("0")) {
                                hideProgressDialog();
                                Toast.makeText(AddNewJobActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }
                        Log.i("LOG_RESPONSE", response);
                        activityAddNewJobBinding.progressBar.setVisibility(View.GONE);
                        activityAddNewJobBinding.imgBtnAddJob.setClickable(true);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activityAddNewJobBinding.progressBar.setVisibility(View.GONE);
                        activityAddNewJobBinding.imgBtnAddJob.setClickable(true);
                        hideProgressDialog();
                        Toast.makeText(AddNewJobActivity.this, "Try Later", Toast.LENGTH_SHORT).show();
                        Log.e("LOG_RESPONSE", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("AuthenticateToken", token);
                        return headers;
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                            return null;
                        }
                    }

               /* @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }*/
                };

                requestQueue.add(stringRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    public void alertDialog() {
        builder = new AlertDialog.Builder(this);
//Uncomment the below code to Set the message and title from the strings.xml file
        //    builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        // builder.setTitle("Are you sure?");
        builder.setMessage("All your changes will be lost")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                        // logout();
                        // finish();
                      /*  Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();*/
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                        /*Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();*/
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Are you sure?");
        alert.show();

    }

    @Override
    public void onBackPressed() {
        alertDialog();
    }


}
