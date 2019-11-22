package com.midwestpilotcars.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.DayWiseAdapter;
import com.midwestpilotcars.apiInterface.CompleteJobApi;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.helpers.Utils;
import com.midwestpilotcars.models.DayAddExpense;
import com.midwestpilotcars.models.addDayExpenses.DayAllExpense;
import com.midwestpilotcars.models.addDayExpenses.DayData;
import com.midwestpilotcars.models.addDayExpenses.DayExpenseData;
import com.midwestpilotcars.models.addDayExpenses.DayExperPerDay;
import com.midwestpilotcars.models.addDayExpenses.DayGasExpense;
import com.midwestpilotcars.models.addDayExpenses.DayMotelExpense;
import com.midwestpilotcars.models.addDayExpenses.DayWiseTravell;
import com.midwestpilotcars.models.addDayExpenses.ExpenseDay;
import com.midwestpilotcars.models.addDayExpenses.OtherExpenseModel;
import com.midwestpilotcars.models.completejob.StatusCompleteJob;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.views.home.MainActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndForm extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnFocusChangeListener, View.OnTouchListener {
    View view;
    private static TransferUtility transferUtility;
    AmazonS3 s3Client;
    File fileToS3;
    private ONGOING ongoingArrayList;
    String job_id = "";
    AlertDialog.Builder builder;
    ImageView  camera_odo;

    TextView odo_empty,
             end_date_comment, total_miles, empty_location,
            empty_date, tx_extra, extra_empty;
    EditText et_odometer, end_location, end_date, end_date_descrp, extra_pay_et, extra_pay_descrp;
    ImageButton next_button;
    private List<DayWiseTravell> day_List = new ArrayList<>();
    private RecyclerView recyclerView;
    private DayWiseAdapter mAdapter;
    String image_url = "";
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_end_form);
        getSupportActionBar().setTitle(getString(R.string.end_form));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeView();

        // callback method to call credentialsProvider method.
        s3credentialsProvider();

        // callback method to call the setTransferUtility method
        setTransferUtility();

        job_id = getIntent().getExtras().getString(AppConstants.JOB_KEY);
        ongoingArrayList = (ONGOING) this.getIntent().getParcelableExtra(AppConstants.JOBS_ONGOING);
        //  String end = ongoingArrayList.getEndTripStatus();

        // Toast.makeText(this, end, Toast.LENGTH_SHORT).show();
        if (Utils.Companion.isNetworkAvailable(this))
            getTotalMiles();
        else
            DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EndForm.this.finish();
                }
            });

        end_date_descrp.setOnTouchListener(this);
        extra_pay_descrp.setOnTouchListener(this);
    }

    public void initializeView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        total_miles = (TextView) findViewById(R.id.total_mile);
        empty_location = (TextView) findViewById(R.id.empty_text_location);

        view = (View) findViewById(R.id.odo_layout);
        camera_odo = (ImageView) view.findViewById(R.id.img_camera_odo);
        et_odometer = (EditText) view.findViewById(R.id.et_odo_meter_reading);
        odo_empty = (TextView) view.findViewById(R.id.empty_text);
        end_location = (EditText) findViewById(R.id.et_end_location);

        view = (View) findViewById(R.id.end_layout);
        end_date = (EditText) view.findViewById(R.id.et_end_date);
        empty_date = (TextView) view.findViewById(R.id.empty_text_date);
        end_date_comment = (TextView) view.findViewById(R.id.tv_end_comment);
        end_date_descrp = (EditText) view.findViewById(R.id.et_date_descrp);
        end_date.setOnClickListener(this);
        end_date.setOnFocusChangeListener(this);
        end_date_comment.setOnClickListener(this);
        next_button = (ImageButton) findViewById(R.id.img_btn_add_job);

        view = (View) findViewById(R.id.extra_layout);
        extra_pay_et = (EditText) view.findViewById(R.id.et_extra_pay);
        extra_pay_descrp = (EditText) view.findViewById(R.id.et_extra_descrp);
        tx_extra = (TextView) view.findViewById(R.id.tv_extra_comment);
        extra_empty = (TextView) view.findViewById(R.id.empty_text_date);
        tx_extra.setOnClickListener(this);

        camera_odo.setOnClickListener(this);
        end_location.setOnClickListener(this);
        next_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_camera_odo:
                id = 0;
                openImagePicker();
                break;

            case R.id.img_btn_add_job:
                boolean isFill = false;
                String odometer_reading = et_odometer.getText().toString();
                String location = end_location.getText().toString();
                String date = end_date.getText().toString();
                String date_feed = end_date_descrp.getText().toString();
                String extra = extra_pay_et.getText().toString();
                String extra_descp = extra_pay_descrp.getText().toString();

                /*if (extra.isEmpty()) {
                    isFill = false;
                    extra_empty.setTextColor(getResources().getColor(R.color.red_color));
                    extra_empty.setVisibility(View.VISIBLE);
                } else {
                    isFill = true;
                    extra_empty.setVisibility(View.GONE);
                }*/

                if (odometer_reading.isEmpty()) {
                    isFill = false;
                    odo_empty.setVisibility(View.VISIBLE);
                    odo_empty.setText("Field can not be empty");
                    odo_empty.setTextColor(getResources().getColor(R.color.red_color));
                } /*else if (image_url.isEmpty()) {
                    isFill = false;
                    odo_empty.setVisibility(View.VISIBLE);
                    odo_empty.setText("Please upload the image");
                    odo_empty.setTextColor(getResources().getColor(R.color.red_color));
                }*/ else {
                    isFill = true;
                    odo_empty.setVisibility(View.GONE);

                }

                if (location.isEmpty()) {
                    isFill = false;
                    empty_location.setVisibility(View.VISIBLE);
                    empty_location.setTextColor(getResources().getColor(R.color.red_color));
                } else {
                    isFill = true;
                    empty_location.setVisibility(View.GONE);

                }

                if (date.isEmpty()) {
                    isFill = false;
                    empty_date.setTextColor(getResources().getColor(R.color.red_color));
                    empty_date.setVisibility(View.VISIBLE);
                } else {
                    isFill = true;
                    empty_date.setVisibility(View.GONE);

                }
                if (!odometer_reading.isEmpty() && !location.isEmpty() && !date.isEmpty() /*&& !extra.isEmpty()*/  ) {
                    if (Utils.Companion.isNetworkAvailable(this))
                        sendData(odometer_reading, location, date, date_feed, extra, extra_descp, image_url);
                    else
                        DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EndForm.this.finish();
                            }
                        });

                    // startActivity(new Intent(EndForm.this,EndFormViewActivity.class));
                } else {
                    Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show();
                }

                //  Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
                break;

            case R.id.et_end_location:
                getLocation(AppConstants.START_LOCATION_REQUEST_CODE);
                break;

            case R.id.tv_extra_comment:
                if (extra_pay_descrp.getVisibility() == View.VISIBLE) {
                    tx_extra.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    tx_extra.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }

                toggleView(extra_pay_descrp);
                break;

            case R.id.tv_end_comment:
                if (end_date_descrp.getVisibility() == View.VISIBLE) {
                    end_date_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    end_date_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(end_date_descrp);
                break;

            case R.id.et_end_date:
                getCurrentDate();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //  finish();
                alertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getLocation(int requestCode) {
        startActivityForResult(new Intent(this, MapActivity.class), requestCode);
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
                        startActivity(new Intent(EndForm.this, MainActivity.class));
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

    private final void openImagePicker() {

        CropImage.activity().
                setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle((CharSequence) "Pickup Image")
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle((CharSequence) "Done")
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)

                .setAutoZoomEnabled(true)

                .start((Activity) this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (id == 0) {
                camera_odo.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
            }
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String imagePath = resultUri.getPath();
                showProgressDialog("Please wait...");
                // Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
                fileToS3 = new File(imagePath);

                uploadFileToS3(EndForm.this, fileToS3);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == AppConstants.START_LOCATION_REQUEST_CODE) {
            if (data != null) {
                end_location.setText(data.getStringExtra("Address"));
            }
        }
    }

    private void selectDate() {
       getCurrentDate();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "yyyy-MM-dd hh:mm:ss"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
         end_date.setText(sdf.format(myCalendar.getTime()));
        //   activityAddNewJobBinding.etSelectDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void getCurrentDate() {
        Calendar mcurrentDate = Calendar.getInstance();
        // mcurrentDate.setTimeInMillis(System.currentTimeMillis() - 1000);
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener = (DatePickerDialog.OnDateSetListener) EndForm.this;

        DatePickerDialog mDate = new DatePickerDialog(EndForm.this, dateSetListener, mYear, mMonth, mDay);
        mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        // mDate.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDate.show();
        String myFormat = "yyyy-MM-dd hh:mm:ss"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
     //   end_date.setText(sdf.format(mcurrentDate.getTime()));
    }

    public final void toggleView(@NotNull View editText) {
        Intrinsics.checkParameterIsNotNull(editText, "editText");
        if (editText.getVisibility() == View.VISIBLE) {
            editText.setVisibility(View.GONE);
        } else {
            editText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            switch (view.getId()) {
                case R.id.et_end_date:
                    selectDate();
                    break;
            }
        }
    }

    public void getTotalMiles() {
        try {
            showProgressDialog(getString(R.string.loading));
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CompleteJobApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CompleteJobApi api = retrofit.create(CompleteJobApi.class);

            Call<DayExpenseData> call = api.getDayExpenseBy(job_id, "ALL", token);

            call.enqueue(new Callback<DayExpenseData>() {
                @Override
                public void onResponse(Call<DayExpenseData> call, Response<DayExpenseData> response) {
                    try {
                        DayExpenseData completeJob = response.body();
                        //       Toast.makeText(EndForm.this, completeJob.getMessage(), Toast.LENGTH_SHORT).show();
                        if (completeJob.getStatus().equals("1")) {
                            if (completeJob.getMessage().equals(getResources().getString(R.string.day_expense))) {
                                DayExperPerDay perDay = completeJob.getData();
                                int sum=0;
                                String total_mile = perDay.getTotal_mile_travelled();
                                total_miles.setText("Total Miles :- " + total_mile);

                                List<DayData> dayData = perDay.getDay_Data();
                                for (int i = 0; i < dayData.size(); i++) {
                                    String day = dayData.get(i).getNo_go_day();

                                    DayWiseTravell day_wise = new DayWiseTravell();

                                    if(dayData.get(i).getMiles_travelled().isEmpty()){
                                        sum = sum + 0;
                                        day_wise.setDay_travell("Day " + String.valueOf(i + 1) + " - " + "0" +" Miles");
                                    }else{
                                        sum = sum + Integer.parseInt(dayData.get(i).getMiles_travelled());
                                        day_wise.setDay_travell("Day " + String.valueOf(i + 1) + " - " + dayData.get(i).getMiles_travelled() +" Miles");
                                    }

                                    day_List.add(day_wise);
                                }
                                total_miles.setText("Total Miles :- " + String.valueOf(sum));
                                mAdapter = new DayWiseAdapter(day_List);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                                hideProgressDialog();
                                //showMessage(completeJob.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        hideProgressDialog();
                        e.printStackTrace();
                    }

                   /* if (completeJob.getStatus().equals("1")) {
                        if (completeJob.getMessage().equals(getResources().getString(R.string.day_expense))) {
                            hideProgressDialog();
                            //showMessage(completeJob.getMessage());
                        }
                    }*/
                }

                @Override
                public void onFailure(Call<DayExpenseData> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(EndForm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void s3credentialsProvider() {
        //     us-east-2:bbcd9ba3-70bc-4c66-ba14-59b57e6637af
        // Initialize the AWS Credential
        CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider =
                new CognitoCachingCredentialsProvider(
                        getApplicationContext(),
                        "us-east-2:bbcd9ba3-70bc-4c66-ba14-59b57e6637af", // Identity Pool ID
                        Regions.US_EAST_2 // Region
                );
        createAmazonS3Client(cognitoCachingCredentialsProvider);
    }

    /**
     * Create a AmazonS3Client constructor and pass the credentialsProvider.
     *
     * @param credentialsProvider
     */
    public void createAmazonS3Client(CognitoCachingCredentialsProvider
                                             credentialsProvider) {

        // Create an S3 client
        s3Client = new AmazonS3Client(credentialsProvider);

        // Set the region of your S3 bucket
        s3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
    }

    public void setTransferUtility() {

        transferUtility = new TransferUtility(s3Client, getApplicationContext());
    }


    public void uploadFileToS3(EndForm expenseDayAdd, File fileToS3) {

        TransferObserver transferObserver = transferUtility.upload(
                "midwestpilotcars",          /* The bucket to upload to */
                this.fileToS3.getName(),/* The key for the uploaded object */
                this.fileToS3, CannedAccessControlList.PublicRead       /* The file where the data to upload exists */
        );

        transferObserverListener(transferObserver);
    }

    public void transferObserverListener(TransferObserver transferObserver) {
        Date expiration = new Date();
        long msec = expiration.getTime();
        msec += 1000 * 6000 * 6000; // 1 hour.
        Date d = new Date();
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse("July 23, 2019");
            expiration.setTime(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            expiration.setTime(msec);
        }
        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010

        transferObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
               /* Toast.makeText(getApplicationContext(), "State Change" + state,
                        Toast.LENGTH_SHORT).show();*/

                ResponseHeaderOverrides overrideHeader = new ResponseHeaderOverrides();
                overrideHeader.setContentType("image/jpeg");
                String mediaUrl = fileToS3.getName();
                GeneratePresignedUrlRequest generatePresignedUrlRequest =
                        new GeneratePresignedUrlRequest("midwestpilotcars", mediaUrl);
                generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
              //  generatePresignedUrlRequest.setExpiration(expiration);
                generatePresignedUrlRequest.setResponseHeaders(overrideHeader);

                URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
                String upload_url = url.toString().substring(0, url.toString().indexOf("?"));
                Log.e("s", url.toString());
                //  return url.toString();
                hideProgressDialog();
                image_url = upload_url.toString();
                camera_odo.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));

            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
              /*  Toast.makeText(getApplicationContext(), "Progress in %" + percentage,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onError(int id, Exception ex) {
                hideProgressDialog();
                Log.e("error", ex.getMessage());
            }

        });
    }

    public void sendData(String odometer_reading, String location, String date, String date_feed, String extra, String extra_descp, String image_url) {
        try {
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            String URL = "http://webfume.net/jasonapp/api/endjobwithVehicleData";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("job_pilot_vehile_final_odometer_reading", odometer_reading);
            params.put("job_pilot_vehile_final_odometer_reading_url", image_url);
            params.put("job_pilot_driver_end_location", location);
            params.put("job_pilot_driver_end_location_latitude", "3.123244343");
            params.put("job_pilot_driver_end_location_longitude", "3.12323424");
            params.put("job_end_date_time", date);
            params.put("job_end_date_time_comment", date_feed);
            params.put("job_extra_pay_to_driver", extra);
            params.put("job_extra_pay_to_driver_comment", extra_descp);
            params.put("job_id", job_id);


            final String mRequestBody = params.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if (status.equals("1")) {
                            if (message.equals("Successfully your job done with vehicle data updation !")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                // String  end_trip_status = data.getString("end_trip_status");
                                Intent intent = new Intent(EndForm.this, EndFormViewActivity.class);
                                intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                intent.putExtra(AppConstants.JOB_KEY, job_id);

                                startActivity(intent);
                                finish();

                            } else if (message.equals("successfully your day job is done")) {

                            }
                        } else if (status.equals("0")) {
                            Toast.makeText(EndForm.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("LOG_RESPONSE", response);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  /*  Toast.makeText(EndForm.this, "job_end_date_time end should be greater then job start date", Toast.LENGTH_SHORT).show();
                    Log.e("LOG_RESPONSE", error.toString());*/

                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);

                        try {
                            JSONObject json = new JSONObject(jsonError);
                            String message = json.getString("message");
                            Toast.makeText(EndForm.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Print Error!
                    }

                    hideProgressDialog();
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
            protected com.android.volley.Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return com.android.volley.Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }*/
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.et_date_descrp:
                if (end_date_descrp.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;

            case R.id.et_extra_descrp:
                if (extra_pay_descrp.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;

        }
        return false;

    }

    public boolean scrollEditView(View view, MotionEvent motionEvent) {
        view.getParent().requestDisallowInterceptTouchEvent(true);
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_SCROLL:
                view.getParent().requestDisallowInterceptTouchEvent(false);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        alertDialog();
    }
}
