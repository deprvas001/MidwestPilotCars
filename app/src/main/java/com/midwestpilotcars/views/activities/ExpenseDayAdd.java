package com.midwestpilotcars.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midwestpilotcars.R;
import com.midwestpilotcars.apiInterface.CompleteJobApi;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.addDayExpenses.DataPerDay;
import com.midwestpilotcars.models.addDayExpenses.DayAllExpense;
import com.midwestpilotcars.models.addDayExpenses.DayDetails;
import com.midwestpilotcars.models.addDayExpenses.DayGasExpense;
import com.midwestpilotcars.models.addDayExpenses.DayMotelExpense;
import com.midwestpilotcars.models.addDayExpenses.ExpenseDay;
import com.midwestpilotcars.models.addDayExpenses.OtherExpenseModel;
import com.midwestpilotcars.models.getAllJobs.Data;
import com.midwestpilotcars.models.getAllJobs.GetAllJobsModel;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.models.getAllJobs.UPCOMING;
import com.midwestpilotcars.network.DayEndData;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExpenseDayAdd extends BaseActivity implements View.OnClickListener, TransferListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {
    private static TransferUtility transferUtility;
    String job_id = "";
    AmazonS3 s3Client;
    private ONGOING ongoingArrayList;
    SwitchCompat end_form;
    AlertDialog.Builder builder;
    File fileToS3;
    int id_camera = 0;
    Boolean isdayChecked = false,isEndJob=false;
    CheckBox no_of_day1;
    View view,costing_view;
    RelativeLayout more_gas_expense_layout, more_motel_layout,
            more_other_layout, mini_layout,costing_form_layout;
    ImageView camer_mile, camera_gas, camera_motel, camera_other, camera_down, camera_mile,
            camera_more_gas, camer_more_motel, camera_more_other;
    ImageButton next_day,end_job;
    TextView gas_comment, motel_comment, other_comment, down_comment,
            mile_comment, empty_gas_expense, empty_gas, empty_motel,
            empty_other, tv_other_add_more, tv_other_comment_more,
            empty_down, empty_mile, tv_more_comment, tv_gas_comment_more,
            tv_add_motel_more, tv_comment_more, mini_comment;
    EditText gas_descrp_comment, motel_descrp_comment, other_descrp_comment,
            down_descrp_comment, et_other_more_expense, et_other_more_comment,
            mile_descrp_comment, et_gas_expense, et_gas_comment_more, mini_amount,
            et_motel_expense, et_other_expense, et_down_expense, motel_expense_more,
            motel_expense_more_comment, mini_descp, no_go_comment,
            et_mile_expense, et_more_expense, et_more_gas_comment, editText_more_gas_price;
    RadioGroup gas_expense_radio, motel_expense_radio, other_expense_radio,
            mile_travl_radio, more_gas_expense_radio, more_motel_expense_radio, more_other_expense_radio;
    String gas_expense_image = "", motel_expense_image = "", other_expense_image = "", down_expense_image = "",
            miles_expense_image = "", more_gas_image = "", more_motel_image = "", more_other_image = "";
    int gas_cash = 1, motel_cash = 2, other_cash = 1, goes_day = 1, per_day = 1, more_gas_cash = 1, more_motel_cash = 2, more_other_cash = 1;
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    Intent intent;
    List<OtherExpenseModel> list_other;
    List<DayGasExpense> list_gas_expense;

    List<DayMotelExpense> list_motel_expense;
    String day_filled = "", total_day_filled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_expense_day_add);

        initializeView();
        // callback method to call credentialsProvider method.
        s3credentialsProvider();

        // callback method to call the setTransferUtility method
        setTransferUtility();

        job_id = getIntent().getExtras().getString(AppConstants.JOB_KEY);
        total_day_filled = getIntent().getExtras().getString(AppConstants.DAY_FILLED);

        // ongoingArrayList = (ONGOING) this.getIntent().getParcelableExtra(AppConstants.JOBS_ONGOING);
        //  day_filled = ongoingArrayList.getDaysFilled();


        if (!total_day_filled.isEmpty() && !total_day_filled.equals("0")) {
            int day_fil = Integer.parseInt(total_day_filled);
            day_fil = day_fil + 1;
            getSupportActionBar().setTitle("Day " + String.valueOf(day_fil));
        } else {
            getSupportActionBar().setTitle("Day 1");
        }
        //   getSupportActionBar().setTitle("Day ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mile_descrp_comment.setOnTouchListener(this);
        no_go_comment.setOnTouchListener(this);
        //  getCompletJob();
        //   sendRequest(ja, jason_motel, jason_other, down_time, down_comment_fed, miles_travelled, mile_comment);
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

    public void initializeView() {
        view = (View) findViewById(R.id.gas_expense_layout);
        camera_gas = (ImageView) view.findViewById(R.id.img_camera_gas);
        gas_comment = (TextView) view.findViewById(R.id.tv_gas_comment);
        et_gas_expense = (EditText) view.findViewById(R.id.et_motel_expenses);
        empty_gas_expense = (TextView) view.findViewById(R.id.empty_text);
        gas_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_gas);
        gas_descrp_comment = (EditText) view.findViewById(R.id.et_gas_comment);
        gas_descrp_comment.setOnTouchListener(this);
        empty_gas = (TextView) view.findViewById(R.id.empty_text);
        tv_more_comment = (TextView) view.findViewById(R.id.tv_gas_more);
        editText_more_gas_price = (EditText) view.findViewById(R.id.et_gas_expenses_more);
        more_gas_expense_layout = (RelativeLayout) view.findViewById(R.id.more_gas_layout);
        et_more_expense = (EditText) view.findViewById(R.id.et_gas_expenses_more);
        et_more_gas_comment = (EditText) view.findViewById(R.id.et_gas_comment_more);
        more_gas_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_gas_more);
        camera_more_gas = (ImageView) view.findViewById(R.id.img_camera_gas_more);
        tv_gas_comment_more = (TextView) view.findViewById(R.id.tv_gas_comment_more);
        et_gas_comment_more = (EditText) view.findViewById(R.id.et_gas_comment_more);
        et_gas_comment_more.setOnTouchListener(this);

        camera_more_gas.setOnClickListener(this);
        camera_gas.setOnClickListener(this);
        gas_comment.setOnClickListener(this);
        tv_more_comment.setOnClickListener(this);
        tv_gas_comment_more.setOnClickListener(this);

        view = (View) findViewById(R.id.motel_expense_layout);
        camera_motel = (ImageView) view.findViewById(R.id.img_camera_motel);
        empty_motel = (TextView) view.findViewById(R.id.empty_text);
        et_motel_expense = (EditText) view.findViewById(R.id.et_motel_expenses);
        motel_comment = (TextView) view.findViewById(R.id.tv_motel_comment);
        motel_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_motel);
        motel_descrp_comment = (EditText) view.findViewById(R.id.et_motel_comment);
        motel_descrp_comment.setOnTouchListener(this);
        et_more_expense = (EditText) view.findViewById(R.id.et_motel_expenses_more);
        motel_expense_more = (EditText) view.findViewById(R.id.et_gas_comment_more);
        motel_expense_more.setOnTouchListener(this);
        tv_add_motel_more = (TextView) view.findViewById(R.id.tv_motel_more);
        tv_comment_more = (TextView) view.findViewById(R.id.tv_motel_comment_more);
        more_motel_layout = (RelativeLayout) view.findViewById(R.id.more_motel_layout);
        more_motel_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_motel_more);
        camer_more_motel = (ImageView) view.findViewById(R.id.img_camera_motel_more);
        motel_comment.setOnClickListener(this);
        camera_motel.setOnClickListener(this);
        tv_add_motel_more.setOnClickListener(this);
        camer_more_motel.setOnClickListener(this);
        tv_comment_more.setOnClickListener(this);

        view = (View) findViewById(R.id.other_expense_layout);
        et_other_expense = (EditText) view.findViewById(R.id.et_other_expenses);
        empty_other = (TextView) view.findViewById(R.id.empty_text);
        camera_other = (ImageView) view.findViewById(R.id.img_camera_other);
        other_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_other);
        other_comment = (TextView) view.findViewById(R.id.tv_other_comment);
        other_descrp_comment = (EditText) view.findViewById(R.id.et_other_comment);
        other_descrp_comment.setOnTouchListener(this);
        tv_other_add_more = (TextView) view.findViewById(R.id.tv_other_more);
        tv_other_comment_more = (TextView) view.findViewById(R.id.tv_other_comment_more);
        et_other_more_expense = (EditText) view.findViewById(R.id.et_other_expenses_more);
        et_other_more_comment = (EditText) view.findViewById(R.id.et_gas_comment_more);
        et_other_more_comment.setOnTouchListener(this);
        camera_more_other = (ImageView) view.findViewById(R.id.img_camera_other_more);
        more_other_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_other_more);
        more_other_layout = (RelativeLayout) view.findViewById(R.id.more_other_layout);

        tv_other_add_more.setOnClickListener(this);
        tv_other_comment_more.setOnClickListener(this);
        camera_more_other.setOnClickListener(this);
        more_other_expense_radio.setOnCheckedChangeListener(this);
        other_comment.setOnClickListener(this);
        camera_other.setOnClickListener(this);

        view = (View) findViewById(R.id.down_town_layout);
        et_down_expense = (EditText) view.findViewById(R.id.et_motel_expenses);
        empty_down = (TextView) view.findViewById(R.id.empty_text);
        camera_down = (ImageView) view.findViewById(R.id.img_camera_down);
        down_comment = (TextView) view.findViewById(R.id.tv_down_comment);
        down_descrp_comment = (EditText) view.findViewById(R.id.et_down_comment);
        down_comment.setOnClickListener(this);
        camera_down.setOnClickListener(this);

        view = (View) findViewById(R.id.mile_travel_layout);
        empty_mile = (TextView) view.findViewById(R.id.empty_text);
        et_mile_expense = (EditText) view.findViewById(R.id.et_motel_expenses);
        camer_mile = (ImageView) view.findViewById(R.id.img_camera_mil_motel);
        mile_comment = (TextView) view.findViewById(R.id.tv_mile_comment);
        mile_descrp_comment = (EditText) view.findViewById(R.id.et_mile_comment);

        mile_comment.setOnClickListener(this);
        camer_mile.setOnClickListener(this);

        next_day = (ImageButton) findViewById(R.id.img_btn_add_job);
        next_day.setOnClickListener(this);

        view = (View) findViewById(R.id.mile_traveld_layout);
        costing_view = view;
        mile_travl_radio = (RadioGroup) view.findViewById(R.id.option_group_miles);
        mini_layout = (RelativeLayout) view.findViewById(R.id.mini_layout);
        mini_comment = (TextView) view.findViewById(R.id.tv_mini_comment);
        mini_amount = (EditText) view.findViewById(R.id.et_mini_expenses);
        mini_descp = (EditText) view.findViewById(R.id.et_mini_comment_decp);

        mini_descp.setOnTouchListener(this);

        mini_comment.setOnClickListener(this);
        gas_expense_radio.setOnCheckedChangeListener(this);
        motel_expense_radio.setOnCheckedChangeListener(this);
        other_expense_radio.setOnCheckedChangeListener(this);
        more_gas_expense_radio.setOnCheckedChangeListener(this);
        more_motel_expense_radio.setOnCheckedChangeListener(this);
        mile_travl_radio.setOnCheckedChangeListener(this);

        view = (View) findViewById(R.id.job_start_dat);
        end_form = (SwitchCompat) view.findViewById(R.id.end_form_switch);

        end_job = (ImageButton)findViewById(R.id.img_btn_end_job);
        end_job.setOnClickListener(this);

        end_form.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isdayChecked = true;
                    //      Toast.makeText(ExpenseDayAdd.this, "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    isdayChecked = false;
                    //   Toast.makeText(ExpenseDayAdd.this, "not checked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //   view = (View)findViewById(R.id.mile_traveld_layout);
        no_of_day1 = (CheckBox) findViewById(R.id.checkbox1);
        no_go_comment = (EditText) findViewById(R.id.no_go_day_comment);
        no_of_day1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    goes_day = 2;
                    costing_view.setVisibility(View.GONE);
                 //   costing_form_layout.setVisibility(View.GONE);
                    no_go_comment.setVisibility(View.VISIBLE);
                    //       Toast.makeText(ExpenseDayAdd.this, "no_go_days", Toast.LENGTH_SHORT).show();
                } else {
                    costing_view.setVisibility(View.VISIBLE);
                   // costing_form_layout.setVisibility(View.VISIBLE);
                    no_go_comment.setVisibility(View.GONE);
                    goes_day = 1;
                    no_go_comment.setText(null);
                    //     Toast.makeText(ExpenseDayAdd.this, "unChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mini_comment:
                if (mini_descp.getVisibility() == View.VISIBLE) {
                    mini_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    mini_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(mini_descp);
                break;

            case R.id.tv_other_comment_more:
                if (et_other_more_comment.getVisibility() == View.VISIBLE) {
                    tv_other_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    tv_other_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(et_other_more_comment);
                break;

            case R.id.tv_other_more:
                if (!et_other_expense.getText().toString().isEmpty() && !other_expense_image.isEmpty()) {
                    empty_other.setVisibility(View.GONE);
                    if (more_other_layout.getVisibility() == View.VISIBLE) {
                        more_other_layout.setVisibility(View.GONE);
                        tv_other_add_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                    } else {
                        more_other_layout.setVisibility(View.VISIBLE);
                        tv_other_add_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                    }
                } else if (et_other_expense.getText().toString().isEmpty()) {

                    empty_other.setText("Field cannot be empty");
                    empty_other.setTextColor(getResources().getColor(R.color.red_color));
                    empty_other.setVisibility(View.VISIBLE);
                } else if (other_expense_image.isEmpty()) {
                    empty_other.setText("Please upload the image");
                    empty_other.setTextColor(getResources().getColor(R.color.red_color));
                    empty_other.setVisibility(View.VISIBLE);
                }

                //  toggleView(more_gas_expense_layout);
                break;

            case R.id.tv_motel_comment_more:
                if (motel_expense_more.getVisibility() == View.VISIBLE) {
                    tv_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    tv_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }

                toggleView(motel_expense_more);
                break;

            case R.id.tv_motel_more:
                if (!et_motel_expense.getText().toString().isEmpty() && !motel_expense_image.isEmpty()) {
                    empty_motel.setVisibility(View.GONE);
                    if (more_motel_layout.getVisibility() == View.VISIBLE) {
                        more_motel_layout.setVisibility(View.GONE);

                        tv_add_motel_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                    } else {
                        more_motel_layout.setVisibility(View.VISIBLE);
                        tv_add_motel_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                    }
                } else if (et_motel_expense.getText().toString().isEmpty()) {

                    empty_motel.setText("Field cannot be empty");
                    empty_motel.setTextColor(getResources().getColor(R.color.red_color));
                    empty_motel.setVisibility(View.VISIBLE);
                } else if (motel_expense_image.isEmpty()) {
                    empty_motel.setText("Please upload the image");
                    empty_motel.setTextColor(getResources().getColor(R.color.red_color));
                    empty_motel.setVisibility(View.VISIBLE);
                }
                //  toggleView(more_gas_expense_layout);
                break;


            case R.id.tv_gas_comment_more:

                if (et_gas_comment_more.getVisibility() == View.VISIBLE) {
                    et_gas_comment_more.setVisibility(View.GONE);
                    tv_gas_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);
                } else {
                    et_gas_comment_more.setVisibility(View.VISIBLE);
                    tv_gas_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);
                }
                //  toggleView(more_gas_expense_layout);
                break;

            case R.id.tv_gas_more:
                if (!et_gas_expense.getText().toString().isEmpty() && !gas_expense_image.isEmpty()) {
                    empty_gas.setVisibility(View.GONE);
                    if (more_gas_expense_layout.getVisibility() == View.VISIBLE) {

                        tv_more_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                        more_gas_expense_layout.setVisibility(View.GONE);
                    } else {
                        more_gas_expense_layout.setVisibility(View.VISIBLE);
                        tv_more_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                    }
                } else if (et_gas_expense.getText().toString().isEmpty()) {
                    empty_gas.setText("Field cannot be empty");
                    empty_gas.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas.setVisibility(View.VISIBLE);

                } else if (gas_expense_image.isEmpty()) {
                    empty_gas.setText("Please upload the image");
                    empty_gas.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas.setVisibility(View.VISIBLE);
                    //  Toast.makeText(this, "Image cannot be empty", Toast.LENGTH_SHORT).show();
                }
                //  toggleView(more_gas_expense_layout);
                break;
            case R.id.tv_gas_comment:
                if (gas_descrp_comment.getVisibility() == View.VISIBLE) {
                    gas_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    gas_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(gas_descrp_comment);
                break;

            case R.id.tv_motel_comment:
                if (motel_descrp_comment.getVisibility() == View.VISIBLE) {
                    motel_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    motel_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(motel_descrp_comment);
                break;

            case R.id.tv_other_comment:
                if (other_descrp_comment.getVisibility() == View.VISIBLE) {
                    other_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    other_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(other_descrp_comment);
                break;

            case R.id.tv_down_comment:
                if (down_descrp_comment.getVisibility() == View.VISIBLE) {
                    down_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    down_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(down_descrp_comment);
                break;

            case R.id.tv_mile_comment:
                if (mile_descrp_comment.getVisibility() == View.VISIBLE) {
                    mile_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    mile_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }

                toggleView(mile_descrp_comment);
                break;

            case R.id.img_camera_mil_motel:
                id_camera = 4;

                //  toggleView(mile_descrp_comment);
                openImagePicker();
                break;

            case R.id.img_camera_gas:
                //  toggleView(mile_descrp_comment);
                id_camera = 0;
                openImagePicker();
                break;

            case R.id.img_camera_gas_more:
                //  toggleView(mile_descrp_comment);
                id_camera = 5;
                openImagePicker();
                break;

            case R.id.img_camera_motel_more:
                //  toggleView(mile_descrp_comment);
                id_camera = 6;
                openImagePicker();
                break;

            case R.id.img_camera_other_more:
                //  toggleView(mile_descrp_comment);
                id_camera = 7;
                openImagePicker();
                break;


            case R.id.img_camera_motel:
                //  toggleView(mile_descrp_comment);
                id_camera = 1;
                openImagePicker();
                break;

            case R.id.img_camera_other:
                //  toggleView(mile_descrp_comment);
                id_camera = 2;
                openImagePicker();
                break;

            case R.id.img_camera_down:
                id_camera = 3;
                //  toggleView(mile_descrp_comment);
                openImagePicker();

                break;
            case R.id.img_btn_add_job:
                if( goes_day == 2 && no_go_comment.getText().toString().isEmpty() ){
                    Toast.makeText(this, "Please add no_go_day comment", Toast.LENGTH_SHORT).show();
                }else{
                    isEndJob = false;
                    createDay();
                }

                break;

            case R.id.img_btn_end_job:
                if( goes_day == 2 && no_go_comment.getText().toString().isEmpty() ){
                    Toast.makeText(this, "Please add no_go_day comment", Toast.LENGTH_SHORT).show();
                }else{
                    confirmEndJob();
                }
                break;
        }

    }

    public final void toggleView(@NotNull View editText) {
        Intrinsics.checkParameterIsNotNull(editText, "editText");
        if (editText.getVisibility() == View.VISIBLE) {
            editText.setVisibility(View.GONE);
        } else {
            editText.setVisibility(View.VISIBLE);
        }

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

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String imagePath = resultUri.getPath();
                 showProgressDialog("Please wait...");
                //  Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
                fileToS3 = new File(imagePath);


                uploadFileToS3(this, fileToS3);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                hideProgressDialog();
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onStateChanged(int id, TransferState state) {

    }

    @Override
    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

    }

    @Override
    public void onError(int id, Exception ex) {

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


    public void uploadFileToS3(ExpenseDayAdd expenseDayAdd, File fileToS3) {

        TransferObserver transferObserver = transferUtility.upload(
                "midwestpilotcars",          /* The bucket to upload to */
                this.fileToS3.getName(),/* The key for the uploaded object */
                this.fileToS3, CannedAccessControlList.PublicRead    /* The file where the data to upload exists */
        );

        transferObserverListener(transferObserver);
    }

    /* This is listener method of the TransferObserver
     * Within this listener method, we get status of uploading and downloading file,
     * to display percentage of the part of file to be uploaded or downloaded to S3
     * It displays an error, when there is a problem in
     * uploading or downloading file to or from S3.
     * @param transferObserver
     */

    public void transferObserverListener(TransferObserver transferObserver) {
        Date expiration = new Date();
        long msec = expiration.getTime();
        msec += 1000 * 6000 * 6000; // 1 hour.
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        DateFormat date_format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String formattedDate = date_format.format(c.getTime());
        Date date = null;
        try {
            date = date_format.parse(formattedDate);
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
                Log.e("s", url.toString());
                String upload_url = url.toString().substring(0, url.toString().indexOf("?"));

                if (id_camera == 0) {
                    gas_expense_image = upload_url.toString();
                    camera_gas.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id_camera == 1) {
                    motel_expense_image = upload_url.toString();
                    camera_motel.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id_camera == 2) {
                    other_expense_image = upload_url.toString();
                    camera_other.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id_camera == 3) {
                    down_expense_image = upload_url.toString();
                    camera_down.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id_camera == 4) {
                    miles_expense_image = upload_url.toString();
                    camer_mile.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id_camera == 5) {
                    more_gas_image = upload_url.toString();
                    camera_more_gas.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id_camera == 6) {
                    more_motel_image = upload_url.toString();
                    camer_more_motel.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id_camera == 7) {
                    more_other_image = upload_url.toString();
                    camera_more_other.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                }
                hideProgressDialog();
                //  return url.toString();
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
               /* Toast.makeText(getApplicationContext(), "Progress in %" + percentage,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onError(int id, Exception ex) {
                hideProgressDialog();
                Log.e("error", ex.getMessage());
            }

        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.option_mile_radio:
                per_day = 1;
                mini_amount.setText(null);
                mini_layout.setVisibility(View.GONE);
                //     Toast.makeText(this, "Miles Travelled", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_day_radio:
                per_day = 2;
                mini_amount.setText(null);
                mini_layout.setVisibility(View.GONE);
                //    Toast.makeText(this, "Per Day Travelled", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_mini_radio:
                per_day = 3;
                mini_layout.setVisibility(View.VISIBLE);
                //    Toast.makeText(this, "Per Day Travelled", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_gas_cash:
                gas_cash = 1;
                //   Toast.makeText(this, "Gas cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_gas_card:
                gas_cash = 2;
                //  Toast.makeText(this, "Gas card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_cash_motel:
                motel_cash = 1;
                //   Toast.makeText(this, "Motel cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_card_motel:
                motel_cash = 2;
                //   Toast.makeText(this, "Motel card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_cash_other:
                other_cash = 1;
                //   Toast.makeText(this, "Other cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_card_other:
                other_cash = 2;
                // Toast.makeText(this, "Other card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_gas_cash_more:
                more_gas_cash = 1;
                //   Toast.makeText(this, "Other cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_gas_card_more:
                more_gas_cash = 2;
                // Toast.makeText(this, "Other card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_motel_cash_more:
                more_motel_cash = 1;
                //   Toast.makeText(this, "Other cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_motel_card_more:
                more_motel_cash = 2;
                //  Toast.makeText(this, "Other card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_other_cash_more:
                more_other_cash = 1;
                //  Toast.makeText(this, "Other cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_other_card_more:
                more_other_cash = 2;
                //   Toast.makeText(this, "Other card", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void sendRequest(JSONArray ja, JSONArray jason_motel, JSONArray jason_other, String down_time, String down_comment_fed, String miles_travelled, String mile_comment) {
        try {
            showProgressDialog("Please wait...");
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            String URL = "http://midwestpilotcars.com/api/dayexpense";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //   RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("gas_expense", String.valueOf(ja));
            params.put("motel_expense", String.valueOf(jason_motel));
            params.put("other_expense", String.valueOf(jason_other));
            params.put("no_of_miles_travelled_comment", mile_comment);
            params.put("no_go_day", String.valueOf(goes_day));
            params.put("no_of_down_time_hours", down_time);
            params.put("no_of_down_time_hours_comment", down_comment_fed);
            params.put("choose_costing_from", String.valueOf(per_day));
            params.put("no_of_miles_travelled", miles_travelled);
            params.put("job_id", job_id);

            params.put("no_go_day_comment", no_go_comment.getText().toString());
            params.put("mini_amount", mini_amount.getText().toString());
            params.put("mini_comment", mini_descp.getText().toString());

            if (isEndJob) {
                params.put("end_trip_status", "1");
            }

            final String mRequestBody = params.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");
                        if (status.equals("1")) {
                            if (message.equals("Day Expense Data inserted successfully!")) {
                                list_other = new ArrayList<OtherExpenseModel>();
                                list_gas_expense = new ArrayList<DayGasExpense>();
                                list_motel_expense = new ArrayList<DayMotelExpense>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                String gas_data = data.getString("gas_expense");
                                String motel_data = data.getString("motel_expense");
                                String ary = data.getString("other_expense");
                                String miles_comment = data.getString("no_of_miles_travelled_comment");
                                String goes_day = data.getString("no_go_day");
                                String down_time = data.getString("no_of_down_time_hours");
                                String down_time_comment = data.getString("no_of_down_time_hours_comment");
                                String costing_form = data.getString("choose_costing_from");
                                String miles_travell = data.getString("no_of_miles_travelled");
                                String job_id = data.getString("job_id");
                                String go_comment = data.getString("no_go_day_comment");
                                String mini_ammount = data.getString("mini_amount");
                                String mini_comment = data.getString("mini_comment");
                                String current_day_expense = data.getString("current_day_expense_id");
                                JSONArray array_other = new JSONArray(ary);
                                JSONArray gas_array = new JSONArray(gas_data);
                                JSONArray motel_array = new JSONArray(motel_data);

                                for (int i = 0; i < gas_array.length(); i++) {
                                    JSONObject obj = gas_array.getJSONObject(0);
                                    String expense_mode = obj.getString("expense_mode");
                                    String expense_comment = obj.getString("expense_comment");
                                    String price = obj.getString("expense_price");
                                    String image_url = obj.getString("expense_image");
                                    DayGasExpense gas_expense = new DayGasExpense();
                                    gas_expense.setExpense_mode(expense_mode);
                                    gas_expense.setExpense_comment(expense_comment);
                                    gas_expense.setExpense_price(price);
                                    gas_expense.setExpense_image(image_url);
                                    list_gas_expense.add(gas_expense);

                                    //    Toast.makeText(ExpenseDayAdd.this, expense_mode, Toast.LENGTH_SHORT).show();
                                }

                                for (int i = 0; i < motel_array.length(); i++) {
                                    JSONObject obj = motel_array.getJSONObject(0);
                                    String expense_mode = obj.getString("expense_mode");
                                    String expense_comment = obj.getString("expense_comment");
                                    String price = obj.getString("expense_price");
                                    String image_url = obj.getString("expense_image");
                                    DayMotelExpense motel_expense = new DayMotelExpense();
                                    motel_expense.setExpense_mode(expense_mode);
                                    motel_expense.setExpense_comment(expense_comment);
                                    motel_expense.setExpense_price(price);
                                    motel_expense.setExpense_image(image_url);
                                    list_motel_expense.add(motel_expense);

                                    //    Toast.makeText(ExpenseDayAdd.this, expense_mode, Toast.LENGTH_SHORT).show();
                                }


                                for (int i = 0; i < array_other.length(); i++) {
                                    JSONObject obj = array_other.getJSONObject(0);
                                    String expense_mode = obj.getString("expense_mode");
                                    String expense_comment = obj.getString("expense_comment");
                                    String price = obj.getString("expense_price");
                                    String image_url = obj.getString("expense_image");
                                    OtherExpenseModel other_expense = new OtherExpenseModel();
                                    other_expense.setExpense_mode(expense_mode);
                                    other_expense.setExpense_comment(expense_comment);
                                    other_expense.setExpense_price(price);
                                    other_expense.setExpense_image(image_url);
                                    list_other.add(other_expense);

                                    //    Toast.makeText(ExpenseDayAdd.this, expense_mode, Toast.LENGTH_SHORT).show();
                                }
                                DayAllExpense expense = new DayAllExpense();
                                expense.setGas_list(list_gas_expense);
                                expense.setMotel_list(list_motel_expense);
                                expense.setOther_list(list_other);
                                expense.setJob_id(job_id);
                                expense.setMiles_travlled_comment(miles_comment);
                                expense.setGo_day(goes_day);
                                expense.setDown_time(down_time);
                                expense.setDown_time_comment(down_time_comment);
                                expense.setCosting_form(costing_form);
                                expense.setMiles_travelled(miles_travell);
                                expense.setGo_day_comment(go_comment);
                                expense.setMini_amount(mini_ammount);
                                expense.setMini_comment(mini_comment);
                                expense.setDay_expense_id(current_day_expense);

                                /*hideProgressDialog();
                                intent = new Intent(ExpenseDayAdd.this, ExpenseDayAdd.class);
                                intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                intent.putExtra(AppConstants.JOB_KEY, job_id);
                                startActivity(intent);
                                finish();*/

                                getDayDetails(expense);

                            } else if (message.equals("successfully your day job is done")) {
                                hideProgressDialog();
                                JSONObject data = jsonObject.getJSONObject("data");
                                String end_trip_status = data.getString("end_trip_status");

                                intent = new Intent(ExpenseDayAdd.this, EndForm.class);
                                intent.putExtra(AppConstants.END_STATUS, end_trip_status);
                                intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                intent.putExtra(AppConstants.JOB_KEY, job_id);
                                startActivity(intent);
                                finish();

                            }
                        } else if (status.equals("0")) {
                            hideProgressDialog();
                            Toast.makeText(ExpenseDayAdd.this, "Gas expense parameter required!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        hideProgressDialog();
                        e.printStackTrace();
                    }
                    Log.i("LOG_RESPONSE", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);

                        try {
                            JSONObject json = new JSONObject(jsonError);
                            String message = json.getString("message");
                            Toast.makeText(ExpenseDayAdd.this, message, Toast.LENGTH_SHORT).show();
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

              /*  @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        if(response.statusCode == 0){

                            Toast.makeText(ExpenseDayAdd.this, response.data.toString(), Toast.LENGTH_SHORT).show();
                        }
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
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!total_day_filled.isEmpty() && !total_day_filled.equals("0")) {

            int value = Integer.parseInt(total_day_filled);

            if (value > 0) {
                getMenuInflater().inflate(R.menu.action_bar_menu, menu);
                List<String> spinnerArray = new ArrayList<String>();

                MenuItem item = menu.findItem(R.id.spinner);
                Spinner spinner = (Spinner) item.getActionView();
                spinnerArray.add("Days");
                for (int i = 1; i <= value; i++) {

                    spinnerArray.add("Day " + String.valueOf(i));

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            String selectedItem = parent.getItemAtPosition(position).toString();
                            intent = new Intent(ExpenseDayAdd.this, ExpenseDayAdd2.class);
                            // ongoingArrayList.setCurrent_day_id(
                            intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                            intent.putExtra(AppConstants.JOB_KEY, job_id);
                            intent.putExtra("days", selectedItem);
                            intent.putExtra("position", position);
                            intent.putExtra("total_days",value);
                            startActivity(intent);
                            spinner.setSelection(0);

                            //   Toast.makeText(ExpenseDayAdd.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                        }

                        // do your stuff

                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }


        return true;

    }

    public void getDayDetails(DayAllExpense expense) {
        try {
            showProgressDialog(getString(R.string.loading));
            String user_type = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getUserType();
            String user_id = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getUserId();
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CompleteJobApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            CompleteJobApi api = retrofit.create(CompleteJobApi.class);


            Call<GetAllJobsModel> call = api.getOngoing(user_type, user_id, "ALL", "0", "10", token);

            call.enqueue(new Callback<GetAllJobsModel>() {
                @Override
                public void onResponse(Call<GetAllJobsModel> call, retrofit2.Response<GetAllJobsModel> response) {
                    try {
                        hideProgressDialog();
                        GetAllJobsModel completeJob = response.body();

                        //   Toast.makeText(EndForm.this, completeJob.getMessage(), Toast.LENGTH_SHORT).show();
                        if (String.valueOf(completeJob.getStatus()).equals("1")) {

                            Data data = completeJob.getData();
                            ArrayList<ONGOING> ongoings = data.getONGOING();
                            for (int i = 0; i < ongoings.size(); i++) {
                                String jobId = ongoings.get(i).getJobId();

                                if (jobId.equals(job_id)) {
                                    ongoingArrayList = ongoings.get(i);


                                    //   ongoingArrayList.setCurrent_day_id(expense.getDay_expense_id());
                                    intent = new Intent(ExpenseDayAdd.this, ExpenseDayAdd.class);
                                    intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                    intent.putExtra(AppConstants.JOB_KEY, job_id);
                                    intent.putExtra(AppConstants.DAY_FILLED, ongoingArrayList.getDaysFilled());
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }
                    } catch (Exception e) {
                        hideProgressDialog();
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<GetAllJobsModel> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(ExpenseDayAdd.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()) {
            case R.id.et_mile_comment:
                if (mile_descrp_comment.hasFocus()) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                break;

            case R.id.no_go_day_comment:
                if (no_go_comment.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;

            case R.id.et_mini_comment_decp:
                if (mini_descp.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;

            case R.id.et_gas_comment:
                if (gas_descrp_comment.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;

            case R.id.et_gas_comment_more:
                if (et_gas_comment_more.hasFocus()) {
                    scrollEditView(view, motionEvent);
                } else if (motel_expense_more.hasFocus()) {
                    scrollEditView(view, motionEvent);
                } else if (et_other_more_comment.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;

            case R.id.et_motel_comment:
                if (motel_descrp_comment.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;
            case R.id.et_other_comment:
                if (other_descrp_comment.hasFocus()) {
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
        //    super.onBackPressed();
        alertDialog();
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
                        startActivity(new Intent(ExpenseDayAdd.this, MainActivity.class));
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

    private void createDay(){
        String gas_expense = et_gas_expense.getText().toString();
        String gas_comment_fed = gas_descrp_comment.getText().toString();
        String motel_expense = et_motel_expense.getText().toString();
        String motel_comment_fed = motel_descrp_comment.getText().toString();
        String other_expense = et_other_expense.getText().toString();
        String other_comment_fed = other_descrp_comment.getText().toString();
        String down_time = et_down_expense.getText().toString();
        String down_comment_fed = down_descrp_comment.getText().toString();
        String miles_travelled = et_mile_expense.getText().toString();
        String mile_comment = mile_descrp_comment.getText().toString();
        String temp = et_other_more_expense.getText().toString();

        String radiovalue = ((RadioButton) findViewById(gas_expense_radio.getCheckedRadioButtonId())).getText().toString();


        if (!gas_expense.isEmpty()) {
            if (gas_expense_image.isEmpty()) {
                DialogUtils.showAlertDialog(this, getString(R.string.gas_image_empty));
                        /*empty_gas.setText("Please upload the image");
                        empty_gas.setTextColor(getResources().getColor(R.color.red_color));
                        empty_gas.setVisibility(View.VISIBLE);*/
                return;
                   /* empty_gas.setText("Field cannot be empty");
                    empty_gas.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas.setVisibility(View.VISIBLE);*/
            }/* else if (gas_expense_image.isEmpty()) {
                    empty_gas.setText("Please upload the image");
                    empty_gas.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas.setVisibility(View.VISIBLE);
                } */ /*else {
                empty_gas.setVisibility(View.GONE);
            }*/
        }

        if (!motel_expense.isEmpty()) {
            if (motel_expense_image.isEmpty()) {
                DialogUtils.showAlertDialog(this, getString(R.string.motel_image_empty));
                return;
            }
            /*empty_motel.setText("Field cannot be empty");
            empty_motel.setTextColor(getResources().getColor(R.color.red_color));
            empty_motel.setVisibility(View.VISIBLE);*/
        } /*else if (motel_expense_image.isEmpty()) {
            empty_motel.setText("Please upload the image");
            empty_motel.setTextColor(getResources().getColor(R.color.red_color));
            empty_motel.setVisibility(View.VISIBLE);
        }*/ /*else {
            empty_motel.setVisibility(View.GONE);
        }*/

        if (!other_expense.isEmpty()) {
            if (other_expense_image.isEmpty()) {
                /*empty_other.setText("Please upload the image");
                empty_other.setTextColor(getResources().getColor(R.color.red_color));
                empty_other.setVisibility(View.VISIBLE);*/
                DialogUtils.showAlertDialog(this, getString(R.string.other_image_empty));
                return;
            }
         /*   empty_other.setText("Field cannot be empty");
            empty_other.setTextColor(getResources().getColor(R.color.red_color));
            empty_other.setVisibility(View.VISIBLE);*/
        }/*else if (!other_expense_image.isEmpty()) {
           if(other_expense.isEmpty()){
               DialogUtils.showAlertDialog(this, getString(R.string.other_empty));
               return;
           }
        }*/

        /*else if (other_expense_image.isEmpty()) {
            empty_other.setText("Please upload the image");
            empty_other.setTextColor(getResources().getColor(R.color.red_color));
            empty_other.setVisibility(View.VISIBLE);
        } else {
            empty_other.setVisibility(View.GONE);
        }*/

        /*if (down_time.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.down_time_empty));
            return;
           *//* empty_down.setText("Field cannot be empty");
            empty_down.setTextColor(getResources().getColor(R.color.red_color));
            empty_down.setVisibility(View.VISIBLE);*//*
        }*//* else {
            empty_down.setVisibility(View.GONE);
        }
*/
        if (miles_travelled.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.miles_empty));
            return;
           /* empty_mile.setText("Field cannot be empty");
            empty_mile.setTextColor(getResources().getColor(R.color.red_color));
            empty_mile.setVisibility(View.VISIBLE);*/
        } /*else {
            empty_mile.setVisibility(View.GONE);
        }*/



        if (/*gas_expense.isEmpty() || motel_expense.isEmpty() || other_expense.isEmpty() ||*/
                /*down_time.isEmpty() ||*/ miles_travelled.isEmpty() /*|| gas_expense_image.isEmpty() ||
                motel_expense_image.isEmpty() || other_expense_image.isEmpty()*/) {
            Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show();
        }
        else {

            try {
                JSONObject object_gas = new JSONObject();
                object_gas.put("expense_mode", String.valueOf(gas_cash));
                object_gas.put("expense_comment", gas_comment_fed);
                object_gas.put("expense_price", gas_expense);
                object_gas.put("expense_image", gas_expense_image);

                JSONArray ja = new JSONArray();
                ja.put(object_gas);


                if (!editText_more_gas_price.getText().toString().isEmpty()) {
                    JSONObject object_gas1 = new JSONObject();
                    object_gas1.put("expense_mode", String.valueOf(more_gas_cash));
                    object_gas1.put("expense_comment", et_more_gas_comment.getText().toString());
                    object_gas1.put("expense_price", editText_more_gas_price.getText().toString());
                    object_gas1.put("expense_image", more_gas_image);
                    ja.put(object_gas1);
                }


                JSONObject object_motel = new JSONObject();
                object_motel.put("expense_mode", String.valueOf(motel_cash));
                object_motel.put("expense_comment", motel_comment_fed);
                object_motel.put("expense_price", motel_expense);
                object_motel.put("expense_image", motel_expense_image);

                JSONArray jason_motel = new JSONArray();
                jason_motel.put(object_motel);

                if (!et_more_expense.getText().toString().isEmpty()) {
                    JSONObject object_motel1 = new JSONObject();
                    object_motel1.put("expense_mode", String.valueOf(more_motel_cash));
                    object_motel1.put("expense_comment", motel_expense_more.getText().toString());
                    object_motel1.put("expense_price", et_more_expense.getText().toString());
                    object_motel1.put("expense_image", more_motel_image);
                    jason_motel.put(object_motel1);

                }


                JSONObject object_other = new JSONObject();
                object_other.put("expense_mode", String.valueOf(other_cash));
                object_other.put("expense_comment", other_comment_fed);
                object_other.put("expense_price", other_expense);
                object_other.put("expense_image", other_expense_image);

                JSONArray jason_other = new JSONArray();
                jason_other.put(object_other);


                if (!temp.isEmpty()) {
                    JSONObject object_other1 = new JSONObject();
                    object_other1.put("expense_mode", String.valueOf(more_other_cash));
                    object_other1.put("expense_comment", et_other_more_comment.getText().toString());
                    object_other1.put("expense_price", et_other_more_expense.getText().toString());
                    object_other1.put("expense_image", more_other_image);

                    jason_other.put(object_other1);
                }

                if(per_day == 3){
                    if(mini_amount.getText().toString().isEmpty()){
                        Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
                    }else{
                        sendRequest(ja, jason_motel, jason_other, down_time, down_comment_fed, miles_travelled, mile_comment);
                    }
                }else{
                    sendRequest(ja, jason_motel, jason_other, down_time, down_comment_fed, miles_travelled, mile_comment);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    private void confirmEndJob(){

        builder = new AlertDialog.Builder(this);
        builder.setMessage(" Are you sure you want to end this job?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isEndJob = true;
                        createDay();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

    }
}
