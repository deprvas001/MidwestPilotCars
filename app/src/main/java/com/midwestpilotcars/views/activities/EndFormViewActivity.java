package com.midwestpilotcars.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.DayWiseAdapter;
import com.midwestpilotcars.apiInterface.CompleteJobApi;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.addDayExpenses.DayAllExpense;
import com.midwestpilotcars.models.addDayExpenses.DayData;
import com.midwestpilotcars.models.addDayExpenses.DayExpenseData;
import com.midwestpilotcars.models.addDayExpenses.DayExperPerDay;
import com.midwestpilotcars.models.addDayExpenses.DayGasExpense;
import com.midwestpilotcars.models.addDayExpenses.DayMotelExpense;
import com.midwestpilotcars.models.addDayExpenses.DayWiseTravell;
import com.midwestpilotcars.models.addDayExpenses.OtherExpenseModel;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.views.home.MainActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EndFormViewActivity extends BaseActivity {
LinearLayout signature_owner,signature_client;
ImageButton submit;
    private ONGOING ongoingArrayList;
    private List<DayWiseTravell> day_List = new ArrayList<>();
    private RecyclerView recyclerView;
    private DayWiseAdapter mAdapter;
    TextView total_miles,odometer_reading,location_value,end_date,text_job_id,no_goes,
            total_show,signature_owner_text,signature_client_text,job_start_date,job_start_title,
            start_job_location,start_job_location_title,no_day_rate,no_day_rate_title,no_of_mini,no_of_mini_count;
    View view;
    AlertDialog.Builder builder;
    String job_id="",owner_signature_url="",client_rep_signature="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_end_form_view);
        getSupportActionBar().setTitle(getString(R.string.end_form));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeView();

        job_id = getIntent().getExtras().getString(AppConstants.JOB_KEY);
        ongoingArrayList = (ONGOING)this.getIntent().getParcelableExtra(AppConstants.JOBS_ONGOING);
        //  String end = ongoingArrayList.getEndTripStatus();

        // Toast.makeText(this, end, Toast.LENGTH_SHORT).show();
         getEndForm2();

        signature_owner = (LinearLayout) findViewById(R.id.signature_owner_layout);
        signature_client = (LinearLayout)findViewById(R.id.signature_client_layout);
        submit = (ImageButton)findViewById(R.id.img_btn_add_job);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(owner_signature_url.isEmpty() ){
                    Toast.makeText(EndFormViewActivity.this, "Please add driver signature.", Toast.LENGTH_SHORT).show();
                }else if(client_rep_signature.isEmpty()){
                    Toast.makeText(EndFormViewActivity.this, "Please add client signature.", Toast.LENGTH_SHORT).show();
                }else{
                    submitForm();
                //    startActivity(new Intent(EndFormViewActivity.this,BackTrip.class));
                }
            }
        });

        signature_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(EndFormViewActivity.this, SignatureScreen.class);
                startActivityForResult(i, 1);
            }
        });

        signature_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
              //    finish();
                alertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getEndForm2(){
        try {
            showProgressDialog(getString(R.string.loading));
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CompleteJobApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CompleteJobApi api = retrofit.create(CompleteJobApi.class);

            Call<DayExpenseData> call = api.getDayExpenseBy(job_id,"ALL", token);

            call.enqueue(new Callback<DayExpenseData>() {
                @Override
                public void onResponse(Call<DayExpenseData> call, Response<DayExpenseData> response) {
                    try{
                        DayExpenseData completeJob = response.body();
                    //    Toast.makeText(EndFormViewActivity.this, completeJob.getMessage(), Toast.LENGTH_SHORT).show();
                        if (completeJob.getStatus().equals("1")) {
                            if (completeJob.getMessage().equals(getResources().getString(R.string.day_expense))) {
                                DayExperPerDay perDay =  completeJob.getData();
                                int sum=0;
                                String total_mile= perDay.getTotal_mile_travelled();

                                Log.e("Midwest Pilot Cars",response.toString());
                                odometer_reading.setText(perDay.getOdometer_reading());
                                location_value.setText(perDay.getEnd_location());
                                end_date.setText(perDay.getEnd_date_time());
                                text_job_id.setText(job_id);
                                no_goes.setText(perDay.getNo_go_day());

                                job_start_date.setText(perDay.getStart_date_time());
                                start_job_location.setText(perDay.getPilot_start_location());
                                no_day_rate.setText(perDay.getCurrent_day_rate());
                                no_of_mini.setText(perDay.getCount_mini_amount());

                                List<DayData> dayData =  perDay.getDay_Data();
                                for (int i=0;i<dayData.size();i++){
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
                                total_show.setText(String.valueOf(sum) +" Miles");
                                total_miles.setText("Total Miles - " +String.valueOf(sum));
                                mAdapter = new DayWiseAdapter(day_List);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                                hideProgressDialog();
                                //showMessage(completeJob.getMessage());
                            }
                        }
                    }catch (Exception e){
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
                    Toast.makeText(EndFormViewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        total_miles = (TextView)findViewById(R.id.total_mile);

        view = (View)findViewById(R.id.odometer_layout);
        odometer_reading = (TextView)view.findViewById(R.id.value);
        view = (View)findViewById(R.id.location_end_layout);
        location_value = (TextView)view.findViewById(R.id.value_location);
        view = (View)findViewById(R.id.end_date_layout);
        end_date = (TextView)view.findViewById(R.id.value_date);

        view = (View)findViewById(R.id.job_id_layout);
        text_job_id = (TextView)view.findViewById(R.id.value_id);

        view = (View)findViewById(R.id.no_goes_layout);
        no_goes = (TextView)view.findViewById(R.id.value_goes);

        view = (View)findViewById(R.id.total_mile_layout);
        total_show = (TextView)view.findViewById(R.id.total_miles);

        signature_owner_text = (TextView)findViewById(R.id.signature_owner);
        signature_client_text = (TextView)findViewById(R.id.signature_client);

        view = (View)findViewById(R.id.job_start_date);
        job_start_date = (TextView)view.findViewById(R.id.value_goes);
        job_start_title = (TextView)view.findViewById(R.id.title);
        job_start_title.setText("Start Date");

        view = (View)findViewById(R.id.job_start_location);
        start_job_location = (TextView)view.findViewById(R.id.value_goes);
        start_job_location_title = (TextView)view.findViewById(R.id.title);
        start_job_location_title.setText("Start Location");

        view = (View)findViewById(R.id.no_day_rate);
        no_day_rate = (TextView)view.findViewById(R.id.value_goes) ;
        no_day_rate_title = (TextView)view.findViewById(R.id.title);
        no_day_rate_title.setText("No. Of No Day Rate");

        view = (View)findViewById(R.id.no_of_mini);
        no_of_mini = (TextView)view.findViewById(R.id.value_goes);
        no_of_mini_count = (TextView)view.findViewById(R.id.title);
        no_of_mini_count.setText("No. Of Mini");

    }

    public void submitForm(){
        showProgressDialog("Please wait...");
        try {
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            String URL = "http://webfume.net/jasonapp/api/endjobwithSignature";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //   RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("job_id" , job_id);
            params.put("job_signature_driver_image_url" , owner_signature_url);
            params.put("job_signature_client_rep_image_url" , client_rep_signature);

            final String mRequestBody = params.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hideProgressDialog();
                    try {
                       // JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if(status.equals("1") ){
                            if(message.equals("Sucessfully your job done with DRIVER/Client signature !")){
                                JSONObject data = jsonObject.getJSONObject("data");
                                String job_id = data.getString("job_id");
                                String trip_status = data.getString("end_trip_status");
                                String amount_pay = data.getString("approx_total_pay_to_driver");

                                Intent intent = new Intent(EndFormViewActivity.this, BackTrip.class);
                                intent.putExtra(AppConstants.JOB_KEY,job_id);
                                /*intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                intent.putExtra(AppConstants.END_STATUS, trip_status);
                                intent.putExtra(AppConstants.APPROX_PAY_AMOUNT,amount_pay);*/
                                startActivity(intent);

                                finish();

                            }
                        }else{
                            Toast.makeText(EndFormViewActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("LOG_RESPONSE", response);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   int status = error.networkResponse.statusCode;
                   if(status == 0){
                       Toast.makeText(EndFormViewActivity.this,"This Job Already EndjobwithSignature", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String url  =  data.getExtras().getString("result");
               // storeImage(bitmap);
             //   Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
                owner_signature_url = url;
                if(!owner_signature_url.isEmpty()){
                    signature_owner_text.setText("Signature of Driver - Done");
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                String url  =  data.getExtras().getString("result");
                // storeImage(bitmap);
           //     Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
                client_rep_signature = url;
                signature_client_text.setText("Signature of Client Rep - Done");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("Midwest",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("Midwest", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("Midwest", "Error accessing file: " + e.getMessage());
        }


    }

    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
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
    public void onBackPressed() {

         alertDialog();
    }

    public void alertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("All your changes will be lost")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(EndFormViewActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Are you sure?");
        alert.show();

    }

    public void showAlertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_client_sign));
        builder.setMessage(getString(R.string.sign_driver))
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent i = new Intent(EndFormViewActivity.this, SignatureScreen.class);
                        startActivityForResult(i, 2);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

    }
}
