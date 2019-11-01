package com.midwestpilotcars.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.DayWiseAdapter;
import com.midwestpilotcars.apiInterface.CompleteJobApi;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.databinding.ActivityExpenseDayAddBinding;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.helpers.Utils;
import com.midwestpilotcars.models.addDayExpenses.DataPerDay;
import com.midwestpilotcars.models.addDayExpenses.DayDetails;
import com.midwestpilotcars.models.addDayExpenses.DayGasExpense;
import com.midwestpilotcars.models.addDayExpenses.DayMotelExpense;
import com.midwestpilotcars.models.addDayExpenses.OtherExpenseModel;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
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


public class ExpenseDayAdd2 extends BaseActivity implements View.OnClickListener, TransferListener, RadioGroup.OnCheckedChangeListener ,View.OnTouchListener{
    private static TransferUtility transferUtility;
    String job_id = "";
    AmazonS3 s3Client;
    private ONGOING ongoingArrayList;
    SwitchCompat end_form;
    File fileToS3;
    AlertDialog.Builder builder;
    int id = 0;
    Boolean isdayChecked = false;
    CheckBox no_of_day1;
    View view,costing_view;
    String day_update_id="";
    RelativeLayout more_gas_expense_layout, more_motel_layout, more_other_layout,mini_layout;
    ImageView camer_mile, camera_gas, camera_motel, camera_other, camera_down, camera_mile,
            camera_more_gas, camer_more_motel, camera_more_other;
    ImageButton next_day,next_day_left,end_day_button;
    TextView gas_comment, motel_comment, other_comment, down_comment,
            mile_comment, empty_gas_expense, empty_gas, empty_motel,
            empty_other, tv_other_add_more, tv_other_comment_more,mini_comment,
            empty_down, empty_mile, tv_more_comment, tv_gas_comment_more, tv_add_motel_more,
            tv_comment_more,next_day_view,end_day_view,next_day_view1;
    EditText gas_descrp_comment, motel_descrp_comment, other_descrp_comment,
            down_descrp_comment, et_other_more_expense, et_other_more_comment,
            mile_descrp_comment, et_gas_expense, et_gas_comment_more,
            et_motel_expense, et_other_expense, et_down_expense, motel_expense_more, motel_expense_more_comment,
            et_mile_expense, et_more_expense, et_more_gas_comment,
            mini_descp,no_go_comment,editText_more_gas_price,mini_amount;
    RadioGroup gas_expense_radio, motel_expense_radio, other_expense_radio,
            mile_travl_radio, more_gas_expense_radio, more_motel_expense_radio, more_other_expense_radio;
    String gas_expense_image = "", motel_expense_image = "", other_expense_image = "", down_expense_image = "",
            miles_expense_image = "", more_gas_image = "", more_motel_image = "", more_other_image = "";
    int gas_cash = 1, motel_cash = 1, other_cash = 1, goes_day = 1, per_day = 1, more_gas_cash = 1, more_motel_cash = 1, more_other_cash = 1;
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    Intent intent;
    List<OtherExpenseModel> list_other;
    List<DayGasExpense> list_gas_expense;
    List<DayMotelExpense> list_motel_expense;
    int total_days=0;
    String day_filled="",total_day="";
    RadioButton gas_cash_button,gas_card_more,gas_card_button,motel_card_button,motel_card_more,other_card_button,other_card_more,option_mile_radio,option_mini_radio;
     int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_expense_day_add2);

        job_id = getIntent().getExtras().getString(AppConstants.JOB_KEY);

        ongoingArrayList = (ONGOING) this.getIntent().getParcelableExtra(AppConstants.JOBS_ONGOING);
      //  day_filled = ongoingArrayList.getDaysFilled();
        /*if(day_filled.isEmpty() || day_filled.equals("0")){
            day_filled = "Day 1";
        }*/
        String day = getIntent().getExtras().getString("days");
        position = getIntent().getExtras().getInt("position");
        total_days = getIntent().getExtras().getInt("total_days");

        initializeView();
        // callback method to call credentialsProvider method.
        s3credentialsProvider();

        // callback method to call the setTransferUtility method
        setTransferUtility();



       //  total_day = getIntent().getExtras().getString("total_days");


        getSupportActionBar().setTitle(day);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mile_descrp_comment.setOnTouchListener(this);
        no_go_comment.setOnTouchListener(this);
        et_gas_comment_more.setOnTouchListener(this);
        motel_descrp_comment.setOnTouchListener(this);
        gas_descrp_comment.setOnTouchListener(this);
        motel_descrp_comment.setOnTouchListener(this);
        motel_expense_more.setOnTouchListener(this);
        other_descrp_comment.setOnTouchListener(this);
        et_other_more_comment.setOnTouchListener(this);
        mini_descp.setOnTouchListener(this);

        if (Utils.Companion.isNetworkAvailable(this))
            getDayDetails();
        else
            DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ExpenseDayAdd2.this.finish();
                }
            });

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
        empty_gas = (TextView) view.findViewById(R.id.empty_text);
        tv_more_comment = (TextView) view.findViewById(R.id.tv_gas_more);
        editText_more_gas_price = (EditText) view.findViewById(R.id.et_gas_expenses_more);
        more_gas_expense_layout = (RelativeLayout) view.findViewById(R.id.more_gas_layout);
        et_more_expense = (EditText) view.findViewById(R.id.et_gas_expenses_more);
        et_more_gas_comment = (EditText) view.findViewById(R.id.et_gas_comment_more);

        gas_cash_button = (RadioButton)view.findViewById(R.id.option_gas_cash) ;
        gas_card_button = (RadioButton)view.findViewById(R.id.option_gas_card);

        more_gas_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_gas_more);
        camera_more_gas = (ImageView) view.findViewById(R.id.img_camera_gas_more);
        gas_card_more = (RadioButton)view.findViewById(R.id.option_gas_card_more) ;
        tv_gas_comment_more = (TextView) view.findViewById(R.id.tv_gas_comment_more);
        et_gas_comment_more = (EditText) view.findViewById(R.id.et_gas_comment_more);


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

        motel_card_button = (RadioButton)view.findViewById(R.id.option_card_motel);
        motel_card_more = (RadioButton)view.findViewById(R.id.option_motel_card_more);

        motel_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_motel);
        motel_descrp_comment = (EditText) view.findViewById(R.id.et_motel_comment);
        et_more_expense = (EditText) view.findViewById(R.id.et_motel_expenses_more);
        motel_expense_more = (EditText) view.findViewById(R.id.et_gas_comment_more);
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
        tv_other_add_more = (TextView) view.findViewById(R.id.tv_other_more);
        other_card_button = (RadioButton) view.findViewById(R.id.option_card_other);
        other_card_more = (RadioButton)view.findViewById(R.id.option_other_card_more) ;

        tv_other_comment_more = (TextView) view.findViewById(R.id.tv_other_comment_more);
        et_other_more_expense = (EditText) view.findViewById(R.id.et_other_expenses_more);
        et_other_more_comment = (EditText) view.findViewById(R.id.et_gas_comment_more);
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

        //
        view = (View) findViewById(R.id.mile_traveld_layout);
        costing_view = view;
        mile_travl_radio = (RadioGroup) view.findViewById(R.id.option_group_miles);
        mini_layout = (RelativeLayout)view.findViewById(R.id.mini_layout) ;
        mini_comment = (TextView)view.findViewById(R.id.tv_mini_comment) ;
        mini_amount =  (EditText) view.findViewById(R.id.et_mini_expenses);
        mini_descp = (EditText)view.findViewById(R.id.et_mini_comment_decp);
        option_mile_radio = (RadioButton)view.findViewById(R.id.option_mile_radio);
        option_mini_radio = (RadioButton)view.findViewById(R.id.option_mini_radio);
        gas_expense_radio.setOnCheckedChangeListener(this);
        motel_expense_radio.setOnCheckedChangeListener(this);
        other_expense_radio.setOnCheckedChangeListener(this);
        mile_travl_radio.setOnCheckedChangeListener(this);
        mini_comment.setOnClickListener(this);
        no_go_comment = (EditText)findViewById(R.id.no_go_day_comment);
        view = (View) findViewById(R.id.job_start_dat);

        mini_comment.setOnClickListener(this);
        end_form = (SwitchCompat) view.findViewById(R.id.end_form_switch);
        view.setVisibility(View.GONE);

        next_day_left = (ImageButton)findViewById(R.id.img_btn_add_job1);
        end_day_button = (ImageButton)findViewById(R.id.img_btn_end_job);
        next_day_view1 = (TextView)findViewById(R.id.day_next1);
        next_day_view = (TextView)findViewById(R.id.day_next);
        end_day_view = (TextView)findViewById(R.id.end_job_view);
        next_day_left.setOnClickListener(this);
        end_day_button.setOnClickListener(this);

        if(position == total_days){
            next_day.setVisibility(View.VISIBLE);
            next_day_view.setVisibility(View.VISIBLE);
            next_day_left.setVisibility(View.GONE);
            next_day_view1.setVisibility(View.GONE);
            end_day_view.setVisibility(View.VISIBLE);
            end_day_button.setVisibility(View.VISIBLE);

        }else{
            next_day.setVisibility(View.GONE);
            next_day_view.setVisibility(View.GONE);
            next_day_left.setVisibility(View.VISIBLE);
            end_day_button.setVisibility(View.GONE);
            next_day_view1.setVisibility(View.VISIBLE);
            end_day_view.setVisibility(View.GONE);
        }


        end_form.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isdayChecked = true;
                //    Toast.makeText(ExpenseDayAdd2.this, "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    isdayChecked = false;
                 //   Toast.makeText(ExpenseDayAdd2.this, "not checked", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //   view = (View)findViewById(R.id.mile_traveld_layout);
        no_of_day1 = (CheckBox) findViewById(R.id.checkbox1);
        no_of_day1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    costing_view.setVisibility(View.GONE);
                    goes_day = 2;
                    no_go_comment.setVisibility(View.VISIBLE);
                 //   Toast.makeText(ExpenseDayAdd2.this, "no_go_days", Toast.LENGTH_SHORT).show();
                } else {
                    costing_view.setVisibility(View.VISIBLE);
                    no_go_comment.setVisibility(View.GONE);
                    goes_day = 1;
                    no_go_comment.setText(null);
                 //   Toast.makeText(ExpenseDayAdd2.this, "unChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mini_comment:
                if (mini_descp.getVisibility() == View.VISIBLE) {
                    mini_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);
                } else {
                    mini_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);
                }
                toggleView(mini_descp );
                break;

            case R.id.tv_other_comment_more:
                if (et_other_more_comment.getVisibility() == View.VISIBLE) {
                    tv_other_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);
                } else {
                    tv_other_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);
                }
                toggleView(et_other_more_comment);
                break;

            case R.id.tv_other_more:
                if(!et_other_expense.getText().toString().isEmpty() && !other_expense_image.isEmpty()){
                    empty_other.setVisibility(View.GONE);
                    if (more_other_layout.getVisibility() == View.VISIBLE) {
                        more_other_layout.setVisibility(View.GONE);
                        tv_other_add_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                    } else {
                        more_other_layout.setVisibility(View.VISIBLE);
                        tv_other_add_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);
                    }
                } else if(et_other_expense.getText().toString().isEmpty()){
                    empty_other.setText("Field cannot be empty");
                    empty_other.setTextColor(getResources().getColor(R.color.red_color));
                    empty_other.setVisibility(View.VISIBLE);
                }else if(other_expense_image.isEmpty()){
                    empty_other.setText("Please upload the image");
                    empty_other.setTextColor(getResources().getColor(R.color.red_color));
                    empty_other.setVisibility(View.VISIBLE);
                }
                //  toggleView(more_gas_expense_layout);
                break;

            case R.id.tv_motel_comment_more:
                if (motel_expense_more.getVisibility() == View.VISIBLE) {
                    tv_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                } else {
                    tv_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);

                }

                toggleView(motel_expense_more);
                break;

            case R.id.tv_motel_more:
                if(!et_motel_expense.getText().toString().isEmpty() && !motel_expense_image.isEmpty()){
                    empty_motel.setVisibility(View.GONE);
                    if (more_motel_layout.getVisibility() == View.VISIBLE) {
                        more_motel_layout.setVisibility(View.GONE);

                        tv_add_motel_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                    } else {
                        more_motel_layout.setVisibility(View.VISIBLE);
                        tv_add_motel_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);

                    }
                }
                else if(et_motel_expense.getText().toString().isEmpty()){

                    empty_motel.setText("Field cannot be empty");
                    empty_motel.setTextColor(getResources().getColor(R.color.red_color));
                    empty_motel.setVisibility(View.VISIBLE);
                }else if(motel_expense_image.isEmpty()){
                    empty_motel.setText("Please upload the image");
                    empty_motel.setTextColor(getResources().getColor(R.color.red_color));
                    empty_motel.setVisibility(View.VISIBLE);
                }
                //  toggleView(more_gas_expense_layout);
                break;

            case R.id.tv_gas_comment_more:

                if (et_gas_comment_more.getVisibility() == View.VISIBLE) {
                    et_gas_comment_more.setVisibility(View.GONE);
                    tv_gas_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);
                } else {
                    et_gas_comment_more.setVisibility(View.VISIBLE);
                    tv_gas_comment_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);
                }
                //  toggleView(more_gas_expense_layout);
                break;

            case R.id.tv_gas_more:
                if(!et_gas_expense.getText().toString().isEmpty() && !gas_expense_image.isEmpty()){
                    empty_gas.setVisibility(View.GONE);
                    if (more_gas_expense_layout.getVisibility() == View.VISIBLE) {

                        tv_more_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                        more_gas_expense_layout.setVisibility(View.GONE);
                    } else {
                        more_gas_expense_layout.setVisibility(View.VISIBLE);
                        tv_more_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);
                    }
                }
                else if(et_gas_expense.getText().toString().isEmpty()){
                    empty_gas.setText("Field cannot be empty");
                    empty_gas.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas.setVisibility(View.VISIBLE);

                }else if(gas_expense_image.isEmpty()){
                    empty_gas.setText("Please upload the image");
                    empty_gas.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas.setVisibility(View.VISIBLE);
                    //  Toast.makeText(this, "Image cannot be empty", Toast.LENGTH_SHORT).show();
                }
                //  toggleView(more_gas_expense_layout);
                break;

            case R.id.tv_gas_comment:
                if (gas_descrp_comment.getVisibility() == View.VISIBLE) {
                    gas_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                } else {
                    gas_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);

                }
                toggleView(gas_descrp_comment);
                break;

            case R.id.tv_motel_comment:
                if (motel_descrp_comment.getVisibility() == View.VISIBLE) {
                    motel_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                } else {
                    motel_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);

                }
                toggleView(motel_descrp_comment);
                break;

            case R.id.tv_other_comment:
                if (other_descrp_comment.getVisibility() == View.VISIBLE) {
                    other_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                } else {
                    other_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);

                }
                toggleView(other_descrp_comment);
                break;

            case R.id.tv_down_comment:
                if (down_descrp_comment.getVisibility() == View.VISIBLE) {
                    down_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                } else {
                    down_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);

                }
                toggleView(down_descrp_comment);
                break;

            case R.id.tv_mile_comment:
                if (mile_descrp_comment.getVisibility() == View.VISIBLE) {
                    mile_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol,0, 0);

                } else {
                    mile_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol,0, 0);

                }

                toggleView(mile_descrp_comment);
                break;


            case R.id.img_camera_mil_motel:
                id = 4;

                //  toggleView(mile_descrp_comment);
                openImagePicker();
                break;

            case R.id.img_camera_gas:
                //  toggleView(mile_descrp_comment);
                id = 0;
                openImagePicker();
                break;

            case R.id.img_camera_gas_more:
                //  toggleView(mile_descrp_comment);
                id = 5;
                openImagePicker();
                break;

            case R.id.img_camera_motel_more:
                //  toggleView(mile_descrp_comment);
                id = 6;
                openImagePicker();
                break;

            case R.id.img_camera_other_more:
                //  toggleView(mile_descrp_comment);
                id= 7;
                openImagePicker();
                break;


            case R.id.img_camera_motel:
                //  toggleView(mile_descrp_comment);
                id = 1;
                openImagePicker();
                break;

            case R.id.img_camera_other:
                //  toggleView(mile_descrp_comment);
                id = 2;
                openImagePicker();
                break;

            case R.id.img_camera_down:
                id = 3;
                //  toggleView(mile_descrp_comment);
                openImagePicker();

                break;
            case R.id.img_btn_add_job:
                if( goes_day == 2 && no_go_comment.getText().toString().isEmpty() ){
                    Toast.makeText(this, "Please add no_go_day comment", Toast.LENGTH_SHORT).show();
                }else{
                    isdayChecked = false;
                    dayUpdate();
                }
                break;

            case R.id.img_btn_add_job1:
                if( goes_day == 2 && no_go_comment.getText().toString().isEmpty() ){
                    Toast.makeText(this, "Please add no_go_day comment", Toast.LENGTH_SHORT).show();
                }else{
                    isdayChecked = false;
                    dayUpdate();
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
                Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
                fileToS3 = new File(imagePath);

                showProgressDialog("Please wait...");
                uploadFileToS3(ExpenseDayAdd2.this, fileToS3);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

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


    public void uploadFileToS3(ExpenseDayAdd2 expenseDayAdd, File fileToS3) {

        TransferObserver transferObserver = transferUtility.upload(
                "midwestpilotcars",          /* The bucket to upload to */
                this.fileToS3.getName(),/* The key for the uploaded object */
                this.fileToS3   , CannedAccessControlList.PublicRead    /* The file where the data to upload exists */
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
            public void onStateChanged(int id_transfer, TransferState state) {
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
                String upload_url = url.toString().substring( 0, url.toString().indexOf("?"));

                if (id == 0) {
                    gas_expense_image = upload_url.toString();
                    camera_gas.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id == 1) {
                    motel_expense_image = upload_url.toString();
                    camera_motel.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id == 2) {
                    other_expense_image = upload_url.toString();
                    camera_other.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id == 3) {
                    down_expense_image = upload_url.toString();
                    camera_down.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id == 4) {
                    miles_expense_image = upload_url.toString();
                    camer_mile.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id == 5) {
                    more_gas_image = upload_url.toString();
                    camera_more_gas.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id == 6) {
                    more_motel_image = upload_url.toString();
                    camer_more_motel.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (id == 7) {
                    more_other_image = upload_url.toString();
                    camera_more_other.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                }
                hideProgressDialog();
                //  return url.toString();
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                /*int percentage = (int) (bytesCurrent / bytesTotal * 100);
                Toast.makeText(getApplicationContext(), "Progress in %" + percentage,
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
                no_go_comment.setText(null);
                mini_layout.setVisibility(View.GONE);
                break;

            case R.id.option_day_radio:
                per_day = 2;
                no_go_comment.setText(null);
                mini_layout.setVisibility(View.GONE);
                break;

            case R.id.option_mini_radio:
                per_day = 3;
                mini_layout.setVisibility(View.VISIBLE);
                break;


            case R.id.option_gas_cash:
                gas_cash = 1;
                break;

            case R.id.option_gas_card:
                gas_cash = 2;
                break;

            case R.id.option_cash_motel:
                motel_cash = 1;
                break;

            case R.id.option_card_motel:
                motel_cash = 2;
                break;

            case R.id.option_cash_other:
                other_cash = 1;
                break;

            case R.id.option_card_other:
                other_cash = 2;
                break;

            case R.id.option_gas_cash_more:
                more_gas_cash = 1;
                break;

            case R.id.option_gas_card_more:
                more_gas_cash = 2;
                break;

            case R.id.option_motel_cash_more:
                more_motel_cash = 1;
                break;

            case R.id.option_motel_card_more:
                more_motel_cash = 2;
                break;

            case R.id.option_other_cash_more:
                more_other_cash = 1;
                break;

            case R.id.option_other_card_more:
                more_other_cash = 2;
                break;

        }
    }

    public void sendRequest(JSONArray ja, JSONArray jason_motel, JSONArray jason_other, String down_time, String down_comment_fed, String miles_travelled, String mile_comment) {
        try {
            showProgressDialog("Please wait...");
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            String URL = "http://webfume.net/jasonapp/api/dayexpense";
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


            if (isdayChecked) {
                params.put("end_trip_status", "1");
            }else{
                params.put("update_day_expense_id",day_update_id);
            }

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
                            if (message.equals("Day Expense Data updated successfully!")) {

                                if(isdayChecked){
                                    intent = new Intent(ExpenseDayAdd2.this, EndForm.class);
                                    intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                    intent.putExtra(AppConstants.JOB_KEY, job_id);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(ExpenseDayAdd2.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }else if(message.equals("successfully your day job is done")){
                                intent = new Intent(ExpenseDayAdd2.this, EndForm.class);
                                intent.putExtra(AppConstants.JOBS_ONGOING, ongoingArrayList);
                                intent.putExtra(AppConstants.JOB_KEY, job_id);
                                startActivity(intent);
                                finish();
                            }
                        } else if (status.equals("0")) {
                            hideProgressDialog();
                            Toast.makeText(ExpenseDayAdd2.this, "Gas expense parameter required!", Toast.LENGTH_SHORT).show();
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

public void getDayDetails(){
try {
        showProgressDialog(getString(R.string.loading));
        String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CompleteJobApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CompleteJobApi api = retrofit.create(CompleteJobApi.class);

        Call<DayDetails> call = api.getDayDetails(job_id,String.valueOf(position), token);

        call.enqueue(new Callback<DayDetails>() {
            @Override
            public void onResponse(Call<DayDetails> call, retrofit2.Response<DayDetails> response) {
                try{
                    hideProgressDialog();
                    DayDetails completeJob = response.body();

                 //   Toast.makeText(EndForm.this, completeJob.getMessage(), Toast.LENGTH_SHORT).show();
                    if (completeJob.getStatus().equals("1")) {
                        if (completeJob.getMesssage().equals(getResources().getString(R.string.per_data))) {
                          //  hideProgressDialog();

                            String down_time = et_down_expense.getText().toString();
                            String down_comment_fed = down_descrp_comment.getText().toString();
                            String miles_travelled = et_mile_expense.getText().toString();
                            String mile_comment = mile_descrp_comment.getText().toString();
                            DataPerDay data = completeJob.getData();
                            day_update_id = data.getDay_expense_id();
                            List<DayGasExpense> gas_expense_list = data.getGas_list();
                            for (int i=0;i<gas_expense_list.size();i++){
                                if(i==0){
                                    DayGasExpense   expense = gas_expense_list.get(0);
                                    et_gas_expense.setText(expense.getExpense_price());
                                    String comment = expense.getExpense_comment();
                                    if(!comment.isEmpty()){
                                        gas_descrp_comment.setText(expense.getExpense_comment());
                                        gas_descrp_comment.setVisibility(View.GONE);
                                    }else{
                                        gas_descrp_comment.setVisibility(View.GONE);
                                    }

                                    gas_expense_image = expense.getExpense_image();
                                    if(!gas_expense_image.isEmpty()){
                                        camera_gas.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                                    }
                                    String payment = expense.getExpense_mode();
                                    if(!payment.isEmpty()){
                                      if(payment.equals("2")){
                                          gas_cash = 2;
                                          gas_card_button.setChecked(true);
                                      }
                                    }

                                }else if(i == 1){

                                    DayGasExpense   expense = gas_expense_list.get(1);
                                    editText_more_gas_price.setText(expense.getExpense_price());
                                    String comment = expense.getExpense_comment();
                                    if(!comment.isEmpty()){
                                        et_more_gas_comment.setText(expense.getExpense_comment());
                                        et_more_gas_comment.setVisibility(View.VISIBLE);
                                    }else{
                                        et_more_gas_comment.setVisibility(View.GONE);
                                    }

                                    more_gas_image = expense.getExpense_image();
                                    if(!more_gas_image.isEmpty()){
                                        camera_more_gas.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                                    }
                                    String payment = expense.getExpense_mode();
                                    if(!payment.isEmpty()){
                                        if(payment.equals("2")){
                                            more_gas_cash = 2;
                                            gas_card_more.setChecked(true);
                                        }
                                    }
                                }
                            }

                            List<DayMotelExpense> motel_expense_list = data.getMotel_list();
                            for (int i=0;i<motel_expense_list.size();i++) {
                                if (i == 0) {
                                    DayMotelExpense expense = motel_expense_list.get(0);
                                    et_motel_expense.setText(expense.getExpense_price());
                                    String comment = expense.getExpense_comment();
                                    if (!comment.isEmpty()) {
                                        motel_descrp_comment.setText(expense.getExpense_comment());
                                        motel_descrp_comment.setVisibility(View.VISIBLE);
                                    } else {
                                        motel_descrp_comment.setVisibility(View.GONE);
                                    }

                                    motel_expense_image = expense.getExpense_image();
                                    if (!motel_expense_image.isEmpty()) {
                                        camera_motel.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                                    }
                                    String payment = expense.getExpense_mode();
                                    if (!payment.isEmpty()) {
                                        if (payment.equals("2")) {
                                            motel_cash = 2;
                                            motel_card_button.setChecked(true);
                                        }
                                    }
                                }else if(i == 1){

                                  /*  object_motel1.put("expense_mode", String.valueOf(more_motel_cash));
                                    object_motel1.put("expense_comment", motel_expense_more.getText().toString());
                                    object_motel1.put("expense_price", et_more_expense.getText().toString());
                                    object_motel1.put("expense_image", more_motel_image);*/

                                    DayMotelExpense expense = motel_expense_list.get(1);
                                    et_more_expense.setText(expense.getExpense_price());
                                    String comment = expense.getExpense_comment();
                                    if (!comment.isEmpty()) {
                                        motel_expense_more.setText(expense.getExpense_comment());
                                        motel_expense_more.setVisibility(View.VISIBLE);
                                    } else {
                                        motel_expense_more.setVisibility(View.GONE);
                                    }

                                    more_motel_image = expense.getExpense_image();
                                    if (!more_motel_image.isEmpty()) {
                                        camer_more_motel.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                                    }
                                    String payment = expense.getExpense_mode();
                                    if (!payment.isEmpty()) {
                                        if (payment.equals("2")) {
                                            more_motel_cash = 2;
                                            motel_card_more.setChecked(true);
                                        }
                                    }
                                }
                            }

                            List<OtherExpenseModel> other_expense_list = data.getOther_list();

                            for (int i=0;i<other_expense_list.size();i++) {
                                if (i == 0) {
                                    OtherExpenseModel expense = other_expense_list.get(0);
                                    et_other_expense.setText(expense.getExpense_price());
                                    String comment = expense.getExpense_comment();
                                    if (!comment.isEmpty()) {
                                        other_descrp_comment.setText(expense.getExpense_comment());
                                        other_descrp_comment.setVisibility(View.VISIBLE);
                                    } else {
                                        other_descrp_comment.setVisibility(View.GONE);
                                    }

                                    other_expense_image = expense.getExpense_image();
                                    if (!other_expense_image.isEmpty()) {
                                        camera_other.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                                    }
                                    String payment = expense.getExpense_mode();
                                    if (!payment.isEmpty()) {
                                        if (payment.equals("2")) {
                                            other_cash = 2;
                                            other_card_button.setChecked(true);
                                        }
                                    }
                                }else if(i==1){
                                    OtherExpenseModel expense = other_expense_list.get(1);
                                    et_other_more_expense.setText(expense.getExpense_price());
                                    String comment = expense.getExpense_comment();
                                    if (!comment.isEmpty()) {
                                        et_other_more_comment.setText(expense.getExpense_comment());
                                        et_other_more_comment.setVisibility(View.VISIBLE);
                                    } else {
                                        et_other_more_comment.setVisibility(View.GONE);
                                    }

                                    more_other_image = expense.getExpense_image();
                                    if (!more_other_image.isEmpty()) {
                                        camera_more_other.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                                    }
                                    String payment = expense.getExpense_mode();
                                    if (!payment.isEmpty()) {
                                        if (payment.equals("2")) {
                                            more_other_cash = 2;
                                            other_card_more.setChecked(true);
                                        }
                                    }
                                }
                            }

                            et_down_expense.setText(data.getDown_time());
                            String comment_down = data.getDown_time_comment();
                            if(!comment_down.isEmpty()){
                                down_descrp_comment.setText(data.getDown_time_comment());
                                down_descrp_comment.setVisibility(View.VISIBLE);
                            }

                            et_mile_expense.setText(data.getMiles_travelled());
                            String comment_mile = data.getMiles_travlled_comment();
                            if(!comment_mile.isEmpty()){
                                mile_descrp_comment.setText(data.getMiles_travlled_comment());
                                mile_descrp_comment.setVisibility(View.VISIBLE);
                            }

                             String costing =  data.getCosting_form();
                             if(!costing.isEmpty()){
                                 if(costing.equals("2")){
                                     per_day = 2;
                                     mini_descp.setText(null);
                                   //  option_mile_radio.setChecked(true);
                                 }else if(costing.equals("1")){
                                     per_day = 1;
                                     mini_descp.setText(null);
                                     //  option_mile_radio.setChecked(t
                                     option_mile_radio.setChecked(true);
                                 }else if(costing.equals("3")){
                                     per_day = 3;
                                     mini_descp.setText(data.getMini_comment());
                                     option_mini_radio.setChecked(true);
                                 }
                             }
                             String goes_day1 = data.getGo_day();
                             if(!goes_day1.isEmpty()){
                                 if(goes_day1.equals("2")){
                                     goes_day = 2;
                                     no_of_day1.setChecked(true);
                                     no_go_comment.setText(data.getGo_day_comment());
                                 }
                             }
                        }
                    }
                }catch (Exception e){
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DayDetails> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(ExpenseDayAdd2.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    } catch (Exception e) {
        hideProgressDialog();
        e.printStackTrace();
    }
}

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()){
            case R.id.et_mile_comment:
                if (mile_descrp_comment.hasFocus()) {
                    scrollEditView(view,motionEvent);
                }
                break;

            case R.id.no_go_day_comment:
                if (no_go_comment.hasFocus()) {
                    scrollEditView(view,motionEvent);
                }
                break;

            case R.id.et_mini_comment_decp:
                if (mini_descp.hasFocus()) {
                    scrollEditView(view,motionEvent);
                }
                break;

            case R.id.et_gas_comment :
                if (gas_descrp_comment.hasFocus()) {
                    scrollEditView(view,motionEvent);
                }
                break;

            case R.id.et_gas_comment_more:
                if (et_gas_comment_more.hasFocus()) {
                    scrollEditView(view,motionEvent);
                }else if(motel_expense_more.hasFocus()){
                    scrollEditView(view,motionEvent);
                }else if(et_other_more_comment.hasFocus()){
                    scrollEditView(view,motionEvent);
                }
                break;

            case R.id.et_motel_comment:
                if (motel_descrp_comment.hasFocus()) {
                    scrollEditView(view,motionEvent);
                }
                break;
            case R.id.et_other_comment:
                if (other_descrp_comment.hasFocus()) {
                    scrollEditView(view,motionEvent);
                }
                break;


        }
        return false;
    }

    public boolean scrollEditView(View view,MotionEvent motionEvent){
        view.getParent().requestDisallowInterceptTouchEvent(true);
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_SCROLL:
                view.getParent().requestDisallowInterceptTouchEvent(false);
                return true;
        }
        return false;
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
                     //   startActivity(new Intent(ExpenseDayAdd2.this, MainActivity.class));
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

    private void dayUpdate(){

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

            if(gas_expense_image.isEmpty()) {
                DialogUtils.showAlertDialog(this, getString(R.string.gas_image_empty));
                return;
            }
            /*empty_gas.setText("Field cannot be empty");
            empty_gas.setTextColor(getResources().getColor(R.color.red_color));
            empty_gas.setVisibility(View.VISIBLE);*/
        } /*else if (gas_expense_image.isEmpty()) {
            empty_gas.setText("Please upload the image");
            empty_gas.setTextColor(getResources().getColor(R.color.red_color));
            empty_gas.setVisibility(View.VISIBLE);
        } else {
            empty_gas.setVisibility(View.GONE);
        }*/

        if (!motel_expense.isEmpty()) {
            if (motel_expense_image.isEmpty()) {
                DialogUtils.showAlertDialog(this, getString(R.string.motel_image_empty));
                return;
            }
           /* empty_motel.setText("Field cannot be empty");
            empty_motel.setTextColor(getResources().getColor(R.color.red_color));
            empty_motel.setVisibility(View.VISIBLE);*/
        } /*else if (motel_expense_image.isEmpty()) {
            empty_motel.setText("Please upload the image");
            empty_motel.setTextColor(getResources().getColor(R.color.red_color));
            empty_motel.setVisibility(View.VISIBLE);
        } else {
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
          /*  empty_other.setText("Field cannot be empty");
            empty_other.setTextColor(getResources().getColor(R.color.red_color));
            empty_other.setVisibility(View.VISIBLE);*/
        } /*else if (other_expense_image.isEmpty()) {
            empty_other.setText("Please upload the image");
            empty_other.setTextColor(getResources().getColor(R.color.red_color));
            empty_other.setVisibility(View.VISIBLE);
        } else {
            empty_other.setVisibility(View.GONE);
        }*/


        /*if (down_time.isEmpty()) {
          *//*  empty_down.setText("Field cannot be empty");
            empty_down.setTextColor(getResources().getColor(R.color.red_color));
            empty_down.setVisibility(View.VISIBLE);*//*
            DialogUtils.showAlertDialog(this, getString(R.string.down_time_empty));
            return;
        }*/ /*else {
            empty_down.setVisibility(View.GONE);
        }*/

        if (miles_travelled.isEmpty()) {
           /* empty_mile.setText("Field cannot be empty");
            empty_mile.setTextColor(getResources().getColor(R.color.red_color));
            empty_mile.setVisibility(View.VISIBLE);*/
            DialogUtils.showAlertDialog(this, getString(R.string.miles_empty));
            return;
        }/* else {
            empty_mile.setVisibility(View.GONE);
        }*/

        if (/*gas_expense.isEmpty() || motel_expense.isEmpty() || other_expense.isEmpty() ||*/
               /* down_time.isEmpty() ||*/ miles_travelled.isEmpty() /*|| gas_expense_image.isEmpty() ||
                motel_expense_image.isEmpty() || other_expense_image.isEmpty()*/) {
        //    Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show();
        } else {

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
                if (Utils.Companion.isNetworkAvailable(this))
                    sendRequest(ja, jason_motel, jason_other, down_time, down_comment_fed, miles_travelled, mile_comment);
                else
                    DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ExpenseDayAdd2.this.finish();
                        }
                    });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
       alertDialog();
    }

    private void confirmEndJob(){

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to end this job?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isdayChecked= true;
                        dayUpdate();
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
