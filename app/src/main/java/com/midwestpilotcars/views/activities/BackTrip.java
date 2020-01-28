package com.midwestpilotcars.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.AllDayExpenseAdapter;
import com.midwestpilotcars.adapters.DayWiseAdapter;
import com.midwestpilotcars.apiInterface.CompleteJobApi;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.databinding.ActivityBackTripBinding;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.helpers.Utils;
import com.midwestpilotcars.models.addDayExpenses.DataPerDay;
import com.midwestpilotcars.models.addDayExpenses.DayDetails;
import com.midwestpilotcars.models.addDayExpenses.DayExperPerDay;
import com.midwestpilotcars.models.addDayExpenses.DayGasExpense;
import com.midwestpilotcars.models.addDayExpenses.DayMotelExpense;
import com.midwestpilotcars.models.addDayExpenses.OtherExpenseModel;
import com.midwestpilotcars.models.getAllJobs.ONGOING;
import com.midwestpilotcars.models.wallet.AllDayAmount;
import com.midwestpilotcars.models.wallet.ApproxAmountPay;
import com.midwestpilotcars.models.wallet.DayExpense;
import com.midwestpilotcars.models.wallet.DriverAmount;
import com.midwestpilotcars.models.wallet.WalletData;
import com.midwestpilotcars.models.wallet.WalletModel;
import com.midwestpilotcars.views.home.MainActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class BackTrip extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {
    ImageView camer_mile, camera_gas, camera_motel, camera_other, camera_down, camera_mile;
    TextView gas_comment, motel_comment, other_comment, down_comment,
            mile_comment, empty_gas_expense, empty_motel_expense, empty_other_expense;
    EditText gas_descrp_comment, motel_descrp_comment, other_descrp_comment, down_descrp_comment,
            mile_descrp_comment, et_gas_expense, et_motel_expense, et_other_expense, et_down_expense, et_mile_expense;
    RadioGroup gas_expense_radio, motel_expense_radio, other_expense_radio, advance_cash;
    int id = 0;
    AllDayExpenseAdapter expenseAdapter;
    RecyclerView recyclerView;
    DriverAmount driverAmount;
    AllDayAmount allDayAmount;
    int check_id = 0;
    File fileToS3;
    String gas_expense_image = "", motel_expense_image = "", other_expense_image = "";
    AlertDialog.Builder builder;
    private static TransferUtility transferUtility;
    AmazonS3 s3Client;
    String total_pay_driver="",main_wallet_amount="";
    ImageButton submit;
    int gas_cash = 1, motel_cash = 1, other_cash = 1, cash_advance = 1, more_gas_cash = 1,
            more_motel_cash = 1, more_other_cash = 1;
    private ONGOING ongoingArrayList;
    String job_id = "";
    View view;
    ActivityBackTripBinding activityBackTripBinding;
    EditText add_more_expense_price, add_more_expense_comment, add_more_expense_motel,
            add_more_motel_descp, add_more_expense_other, add_more_other_descp, cash_amount_et;
    TextView add_more_gas, add_more_gas_comment, add_more_motel,
            add_more_motel_comment, add_more_other, add_more_other_comment,et_approx_amount,et_wallet_amount,et_pending_amount;
    RadioGroup more_gas_radio, more_motel_radio, more_other_radio;
    RelativeLayout gas_more_layout, motel_more_layout, other_more_layout;
    String image_gas_more = "", image_motel_more = "", image_other_more = "";
    ImageView add_more_gas_image, add_more_motel_image, add_more_other_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_back_trip);
        activityBackTripBinding = DataBindingUtil.setContentView(this,R.layout.activity_back_trip);
        getSupportActionBar().setTitle(getString(R.string.back_trip));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeView();

        if( getIntent().getExtras() != null)
        {
            job_id = getIntent().getExtras().getString(AppConstants.JOB_KEY);
           /* ongoingArrayList = (ONGOING) this.getIntent().getParcelableExtra(AppConstants.JOBS_ONGOING);
            String amount = getIntent().getExtras().getString(AppConstants.APPROX_PAY_AMOUNT);*/

            getDriverWallet();
        }

        // callback method to call credentialsProvider method.
        s3credentialsProvider();

        // callback method to call the setTransferUtility method
        setTransferUtility();
        gas_descrp_comment.setOnTouchListener(this);
        motel_descrp_comment.setOnTouchListener(this);
        add_more_expense_comment.setOnTouchListener(this);
        add_more_motel_descp.setOnTouchListener(this);
        other_descrp_comment.setOnTouchListener(this);
        add_more_other_descp.setOnTouchListener(this);
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

    public void initializeView() {
        submit = (ImageButton) findViewById(R.id.img_btn_add_job);
        submit.setOnClickListener(this);
        view = (View) findViewById(R.id.gas_expense_layout);
        camera_gas = (ImageView) view.findViewById(R.id.img_camera_gas);
        gas_comment = (TextView) view.findViewById(R.id.tv_gas_comment);
        et_gas_expense = (EditText) view.findViewById(R.id.et_motel_expenses);
        empty_gas_expense = (TextView) view.findViewById(R.id.empty_text);
        gas_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_gas);
        gas_descrp_comment = (EditText) view.findViewById(R.id.et_gas_comment);
        add_more_expense_price = (EditText) view.findViewById(R.id.et_gas_expenses_more);
        add_more_expense_comment = (EditText) view.findViewById(R.id.et_gas_comment_more);
        gas_more_layout = (RelativeLayout) view.findViewById(R.id.more_gas_layout);
        add_more_gas = (TextView) view.findViewById(R.id.tv_gas_more);
        add_more_gas_comment = (TextView) view.findViewById(R.id.tv_gas_comment_more);
        more_gas_radio = (RadioGroup) view.findViewById(R.id.option_group_gas_more);
        add_more_gas_image = (ImageView) view.findViewById(R.id.img_camera_gas_more);
        add_more_gas_image.setOnClickListener(this);
        add_more_gas.setOnClickListener(this);
        add_more_gas_comment.setOnClickListener(this);
        camera_gas.setOnClickListener(this);
        gas_comment.setOnClickListener(this);
        more_gas_radio.setOnCheckedChangeListener(this);

        view = (View) findViewById(R.id.motel_expense_layout);
        camera_motel = (ImageView) view.findViewById(R.id.img_camera_motel);
        et_motel_expense = (EditText) view.findViewById(R.id.et_motel_expenses);
        motel_comment = (TextView) view.findViewById(R.id.tv_motel_comment);
        motel_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_motel);
        empty_motel_expense = (TextView) view.findViewById(R.id.empty_text);
        motel_descrp_comment = (EditText) view.findViewById(R.id.et_motel_comment);
        add_more_expense_motel = (EditText) view.findViewById(R.id.et_motel_expenses_more);
        add_more_motel_descp = (EditText) view.findViewById(R.id.et_gas_comment_more);
        add_more_motel = (TextView) view.findViewById(R.id.tv_motel_more);
        add_more_motel_comment = (TextView) view.findViewById(R.id.tv_motel_comment_more);
        more_motel_radio = (RadioGroup) view.findViewById(R.id.option_group_motel_more);
        motel_more_layout = (RelativeLayout) view.findViewById(R.id.more_motel_layout);
        add_more_motel_image = (ImageView) view.findViewById(R.id.img_camera_motel_more);

        more_motel_radio.setOnCheckedChangeListener(this);
        add_more_motel_image.setOnClickListener(this);
        add_more_motel_comment.setOnClickListener(this);
        add_more_motel.setOnClickListener(this);
        motel_comment.setOnClickListener(this);
        camera_motel.setOnClickListener(this);


        view = (View) findViewById(R.id.other_expense_layout);
        et_other_expense = (EditText) view.findViewById(R.id.et_other_expenses);
        camera_other = (ImageView) view.findViewById(R.id.img_camera_other);
        other_expense_radio = (RadioGroup) view.findViewById(R.id.option_group_other);
        other_comment = (TextView) view.findViewById(R.id.tv_other_comment);
        empty_other_expense = (TextView) view.findViewById(R.id.empty_text);
        other_descrp_comment = (EditText) view.findViewById(R.id.et_other_comment);
        add_more_expense_other = (EditText) view.findViewById(R.id.et_other_expenses_more);
        add_more_other_descp = (EditText) view.findViewById(R.id.et_gas_comment_more);
        add_more_other = (TextView) view.findViewById(R.id.tv_other_more);
        add_more_other_comment = (TextView) view.findViewById(R.id.tv_other_comment_more);
        more_other_radio = (RadioGroup) view.findViewById(R.id.option_group_other_more);
        other_more_layout = (RelativeLayout) view.findViewById(R.id.more_other_layout);
        add_more_other_image = (ImageView) view.findViewById(R.id.img_camera_other_more);
        more_other_radio.setOnCheckedChangeListener(this);
        add_more_other_image.setOnClickListener(this);
        add_more_other_comment.setOnClickListener(this);
        add_more_other.setOnClickListener(this);
        other_comment.setOnClickListener(this);
        camera_other.setOnClickListener(this);

        view = (View) findViewById(R.id.advance_layout);
        et_approx_amount = (TextView) view.findViewById(R.id.approx_amount);
        et_wallet_amount = (TextView)view.findViewById(R.id.wallet_amount);
        et_pending_amount = (TextView) view.findViewById(R.id.pending_amount);
        cash_amount_et = (EditText) view.findViewById(R.id.cash_amount_edittext);
        advance_cash = (RadioGroup) view.findViewById(R.id.option_group_cash);
        activityBackTripBinding.advanceLayout.verifyButton.setOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);



        gas_expense_radio.setOnCheckedChangeListener(this);
        motel_expense_radio.setOnCheckedChangeListener(this);
        other_expense_radio.setOnCheckedChangeListener(this);
        advance_cash.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_other_comment_more:
                if (add_more_other_descp.getVisibility() == View.VISIBLE) {
                    add_more_other_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                } else {
                    add_more_other_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }
                toggleView(add_more_other_descp);
                break;


            case R.id.tv_other_more:
                if (!et_other_expense.getText().toString().isEmpty() && !other_expense_image.isEmpty()) {
                    empty_other_expense.setVisibility(View.GONE);
                    if (other_more_layout.getVisibility() == View.VISIBLE) {
                        other_more_layout.setVisibility(View.GONE);
                        add_more_other.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                    } else {
                        other_more_layout.setVisibility(View.VISIBLE);
                        add_more_other.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                    }
                } else if (et_other_expense.getText().toString().isEmpty()) {
                    empty_other_expense.setText("Field cannot be empty");
                    empty_other_expense.setTextColor(getResources().getColor(R.color.red_color));
                    empty_other_expense.setVisibility(View.VISIBLE);
                } else if (other_expense_image.isEmpty()) {
                    empty_other_expense.setText("Please upload the image");
                    empty_other_expense.setTextColor(getResources().getColor(R.color.red_color));
                    empty_other_expense.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.tv_motel_comment_more:
                if (add_more_motel_descp.getVisibility() == View.VISIBLE) {
                    add_more_motel_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);
                } else {
                    add_more_motel_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                }

                toggleView(add_more_motel_descp);
                break;

            case R.id.tv_motel_more:
                if (!et_motel_expense.getText().toString().isEmpty() && !motel_expense_image.isEmpty()) {
                    empty_motel_expense.setVisibility(View.GONE);
                    if (motel_more_layout.getVisibility() == View.VISIBLE) {
                        motel_more_layout.setVisibility(View.GONE);
                        add_more_motel.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                    } else {
                        motel_more_layout.setVisibility(View.VISIBLE);
                        add_more_motel.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);
                    }
                } else if (et_motel_expense.getText().toString().isEmpty()) {
                    empty_motel_expense.setText("Field cannot be empty");
                    empty_motel_expense.setTextColor(getResources().getColor(R.color.red_color));
                    empty_motel_expense.setVisibility(View.VISIBLE);

                } else if (motel_expense_image.isEmpty()) {
                    empty_motel_expense.setText("Please upload the image");
                    empty_motel_expense.setTextColor(getResources().getColor(R.color.red_color));
                    empty_motel_expense.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tv_gas_comment_more:
                if (add_more_expense_comment.getVisibility() == View.VISIBLE) {
                    add_more_expense_comment.setVisibility(View.GONE);
                    add_more_gas_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);
                } else {
                    add_more_expense_comment.setVisibility(View.VISIBLE);
                    add_more_gas_comment.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);
                }
                //  toggleView(more_gas_expense_layout);
                break;


            case R.id.tv_gas_more:
                if (!et_gas_expense.getText().toString().isEmpty() && !gas_expense_image.isEmpty()) {
                    empty_gas_expense.setVisibility(View.GONE);
                    if (gas_more_layout.getVisibility() == View.VISIBLE) {
                        gas_more_layout.setVisibility(View.GONE);
                        add_more_gas.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_plus_symbol, 0, 0);

                    } else {
                        gas_more_layout.setVisibility(View.VISIBLE);
                        add_more_gas.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cross_symbol, 0, 0);

                    }
                } else if (et_gas_expense.getText().toString().isEmpty()) {
                    empty_gas_expense.setText("Field cannot be empty");
                    empty_gas_expense.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas_expense.setVisibility(View.VISIBLE);

                } else if (gas_expense_image.isEmpty()) {
                    empty_gas_expense.setText("Please upload the image");
                    empty_gas_expense.setTextColor(getResources().getColor(R.color.red_color));
                    empty_gas_expense.setVisibility(View.VISIBLE);
                }

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

            case R.id.img_camera_gas_more:
                //  toggleView(mile_descrp_comment);
                check_id = 3;
                openImagePicker();
                break;

            case R.id.img_camera_gas:
                //  toggleView(mile_descrp_comment);
                check_id = 0;
                openImagePicker();
                break;

            case R.id.img_camera_motel:
                //  toggleView(mile_descrp_comment);
                check_id = 1;
                openImagePicker();
                break;

            case R.id.img_camera_other:
                //  toggleView(mile_descrp_comment);
                check_id = 2;

                openImagePicker();
                break;

            case R.id.img_camera_motel_more:
//  toggleView(mile_descrp_comment);
                check_id = 4;

                openImagePicker();
                break;

            case R.id.img_camera_other_more:
//  toggleView(mile_descrp_comment);
                check_id = 5;

                openImagePicker();
                break;

            case R.id.verify_button:
                validateAmount(driverAmount,allDayAmount);
                break;

            case R.id.img_btn_add_job:
                String gas_expense = et_gas_expense.getText().toString();
                String gas_comment_fed = gas_descrp_comment.getText().toString();
                String motel_expense = et_motel_expense.getText().toString();
                String motel_comment_fed = motel_descrp_comment.getText().toString();
                String other_expense = et_other_expense.getText().toString();
                String other_comment_fed = other_descrp_comment.getText().toString();

                if (!gas_expense.isEmpty()) {
                    if (gas_expense_image.isEmpty()) {
                        DialogUtils.showAlertDialog(this, getString(R.string.gas_image_empty));
                        return;
                    }
                }
                if (!motel_expense.isEmpty()) {
                    if (motel_expense_image.isEmpty()) {
                        DialogUtils.showAlertDialog(this, getString(R.string.motel_image_empty));
                        return;
                    }
                }
                if (!other_expense.isEmpty()) {
                    if (other_expense_image.isEmpty()) {
                        DialogUtils.showAlertDialog(this, getString(R.string.other_image_empty));
                        return;
                    }

                }
                /*if (gas_expense.isEmpty() || motel_expense.isEmpty() || other_expense.isEmpty() || other_expense_image.isEmpty()
                        || motel_expense_image.isEmpty() || gas_expense_image.isEmpty()) {
                    Toast.makeText(this, "Please fill all details.", Toast.LENGTH_SHORT).show();
                }*/ /*else {*/

                    try {
                        JSONObject object_gas = new JSONObject();
                        object_gas.put("expense_mode", String.valueOf(gas_cash));
                        object_gas.put("expense_comment", gas_comment_fed);
                        object_gas.put("expense_price", gas_expense);
                        object_gas.put("expense_image", gas_expense_image);

                        JSONArray ja = new JSONArray();
                        ja.put(object_gas);
                        if (!add_more_expense_price.getText().toString().isEmpty()) {
                            JSONObject object_gas1 = new JSONObject();
                            object_gas1.put("expense_mode", String.valueOf(more_gas_cash));
                            object_gas1.put("expense_comment", add_more_expense_comment.getText().toString());
                            object_gas1.put("expense_price", add_more_expense_price.getText().toString());
                            object_gas1.put("expense_image", image_gas_more);
                            ja.put(object_gas1);
                        }

                        JSONObject object_motel = new JSONObject();
                        object_motel.put("expense_mode", String.valueOf(motel_cash));
                        object_motel.put("expense_comment", motel_comment_fed);
                        object_motel.put("expense_price", motel_expense);
                        object_motel.put("expense_image", motel_expense_image);

                        JSONArray jason_motel = new JSONArray();
                        jason_motel.put(object_motel);

                        if (!add_more_expense_motel.getText().toString().isEmpty()) {
                            JSONObject object_motel1 = new JSONObject();
                            object_motel1.put("expense_mode", String.valueOf(more_motel_cash));
                            object_motel1.put("expense_comment", add_more_motel_descp.getText().toString());
                            object_motel1.put("expense_price", add_more_expense_motel.getText().toString());
                            object_motel1.put("expense_image", image_motel_more);
                            jason_motel.put(object_motel1);
                        }

                        JSONObject object_other = new JSONObject();
                        object_other.put("expense_mode", String.valueOf(other_cash));
                        object_other.put("expense_comment", other_comment_fed);
                        object_other.put("expense_price", other_expense);
                        object_other.put("expense_image", other_expense_image);

                        JSONArray jason_other = new JSONArray();
                        jason_other.put(object_other);

                        if (!add_more_expense_other.getText().toString().isEmpty()) {
                            JSONObject object_other1 = new JSONObject();
                            object_other1.put("expense_mode", String.valueOf(more_other_cash));
                            object_other1.put("expense_comment", add_more_other_descp.getText().toString());
                            object_other1.put("expense_price", add_more_expense_other.getText().toString());
                            object_other1.put("expense_image", image_other_more);
                            jason_other.put(object_other1);
                        }

                        if (Utils.Companion.isNetworkAvailable(this)){
                            if(validateAmount(driverAmount,allDayAmount)){
                                //  sendRequest(ja, jason_motel, jason_other);
                                Toast.makeText(this, "Request Success", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BackTrip.this.finish();
                                }
                            });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
               /* }*/
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {


            case R.id.option_gas_cash:
                gas_cash = 1;
                // Toast.makeText(this, "Gas cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_gas_card:
                gas_cash = 2;
                // Toast.makeText(this, "Gas card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_cash_motel:
                motel_cash = 1;
                // Toast.makeText(this, "Motel cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_card_motel:
                motel_cash = 2;
                // Toast.makeText(this, "Motel card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_cash_other:
                other_cash = 1;
                // Toast.makeText(this, "Other cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_card_other:
                other_cash = 2;
                // Toast.makeText(this, "Other card", Toast.LENGTH_SHORT).show();
                break;


            case R.id.option_gas_cash_more:
                more_gas_cash = 1;
                break;

            case R.id.option_gas_card_more:
                more_gas_cash = 2;
                break;

            case R.id.option_give_radio:
                cash_advance = 1;
                activityBackTripBinding.advanceLayout.verifyButton.setVisibility(View.VISIBLE);
                cash_amount_et.setVisibility(View.VISIBLE);
                break;

            case R.id.option_take_radio:
                cash_advance = 2;
                activityBackTripBinding.advanceLayout.verifyButton.setVisibility(View.GONE);
                cash_amount_et.setVisibility(View.GONE);
                resetView();
                cash_amount_et.setText(null);
                break;

            case R.id.option_motel_cash_more:
                more_motel_cash = 1;
                // Toast.makeText(this, "Other cash", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_motel_card_more:
                more_motel_cash = 2;
                // Toast.makeText(this, "Other card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.option_other_cash_more:
                more_other_cash = 1;
                break;

            case R.id.option_other_card_more:
                more_other_cash = 2;
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
                //    Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
                showProgressDialog("Please wait...");

                fileToS3 = new File(imagePath);
                uploadFileToS3(BackTrip.this, fileToS3);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void uploadFileToS3(BackTrip expenseDayAdd, File fileToS3) {

        TransferObserver transferObserver = transferUtility.upload(
                "midwestpilotcars",          /* The bucket to upload to */
                this.fileToS3.getName(),/* The key for the uploaded object */
                this.fileToS3, CannedAccessControlList.PublicRead      /* The file where the data to upload exists */
        );

        transferObserverListener(transferObserver);
    }

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
            public void onStateChanged(int id_transfer, TransferState state) {
               /* Toast.makeText(getApplicationContext(), "State Change" + state,
                        Toast.LENGTH_SHORT).show();*/

                ResponseHeaderOverrides overrideHeader = new ResponseHeaderOverrides();
                overrideHeader.setContentType("image/jpeg");
                String mediaUrl = fileToS3.getName();
                GeneratePresignedUrlRequest generatePresignedUrlRequest =
                        new GeneratePresignedUrlRequest("midwestpilotcars", mediaUrl);
                generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
             //   generatePresignedUrlRequest.setExpiration(expiration);
                generatePresignedUrlRequest.setResponseHeaders(overrideHeader);

                URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
                Log.e("s", url.toString());
                String upload_url = url.toString().substring(0, url.toString().indexOf("?"));

                if (check_id == 0) {
                    gas_expense_image = upload_url;
                    camera_gas.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (check_id == 1) {
                    motel_expense_image = upload_url;
                    camera_motel.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (check_id == 2) {
                    other_expense_image = upload_url;
                    camera_other.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (check_id == 3) {
                    image_gas_more = upload_url;
                    add_more_gas_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (check_id == 4) {
                    image_motel_more = upload_url;
                    add_more_motel_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                } else if (check_id == 5) {
                    image_other_more = upload_url;
                    add_more_other_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_photo_camera));
                }
                hideProgressDialog();
                //  return url.toString();
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent / bytesTotal * 100);
                /*Toast.makeText(getApplicationContext(), "Progress in %" + percentage,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("error", ex.getMessage());
                hideProgressDialog();
            }

        });
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

    public void alertDialog1() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Job has been completed!")
                .setCancelable(false)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(BackTrip.this, MainActivity.class));
                        finish();
                    }
                })
               ;
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Success");
        alert.show();

    }

    public void sendRequest(JSONArray ja, JSONArray jason_motel, JSONArray jason_other) {
        try {
            showProgressDialog("Please wait...");
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            String URL = "http://webfume.net/jasonapp/api/endjobwithBackTrip";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //   RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("gas_expense", String.valueOf(ja));
            params.put("motel_expense", String.valueOf(jason_motel));
            params.put("other_expense", String.valueOf(jason_other));
            params.put("job_id", job_id);
            params.put("cash_advance_decision", String.valueOf(cash_advance));
            if(cash_amount_et.getText().toString().isEmpty()){
                params.put("give_back_cash", "0");
            }else{
                params.put("give_back_cash", cash_amount_et.getText().toString());
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
                            if (message.equals("Backtrip and Job completion done successfully!")) {
                                hideProgressDialog();
                                alertDialog1();

                            } else if (message.equals("successfully your day job is done")) {
                                hideProgressDialog();
                                Toast.makeText(BackTrip.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else if (status.equals("0")) {
                            hideProgressDialog();
                            Toast.makeText(BackTrip.this, message, Toast.LENGTH_SHORT).show();
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
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        switch (response.statusCode) {
                            case 400:
                                Toast.makeText(BackTrip.this, "Main Wallet should be with sufficient Balance!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        //Additional cases
                    }
                    hideProgressDialog();
                    Log.e("LOG_RESPONSE", error.toString());
                    // Toast.makeText(BackTrip.this, "", Toast.LENGTH_SHORT).show();
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
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()) {
            case R.id.et_gas_comment:
                if (gas_descrp_comment.hasFocus()) {
                    scrollEditView(view, motionEvent);
                }
                break;

            case R.id.et_gas_comment_more:
                if (add_more_expense_comment.hasFocus()) {
                    scrollEditView(view, motionEvent);
                } else if (add_more_motel_descp.hasFocus()) {
                    scrollEditView(view, motionEvent);
                } else if (add_more_other_descp.hasFocus()) {
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

    public void alertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("All your changes will be lost")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(BackTrip.this, MainActivity.class));
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

    @Override
    public void onBackPressed() {
        alertDialog();
    }

    public  void getDriverWallet(){
        try {
            showProgressDialog(getString(R.string.loading));
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CompleteJobApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CompleteJobApi api = retrofit.create(CompleteJobApi.class);

            Call<ApproxAmountPay> call = api.getApproxAmount(token,job_id);

            call.enqueue(new Callback<ApproxAmountPay>() {
                @Override
                public void onResponse(Call<ApproxAmountPay> call, retrofit2.Response<ApproxAmountPay> response) {
                    try{
                        ApproxAmountPay status = response.body();

                        if (status.getStatus().equals("1")) {
                            if (status.getMessage().equals(getResources().getString(R.string.get_approx_amount))) {
                                DriverAmount data = status.getData();
                                String approx_amount = data.getApprox_pay();
                                String wallet_amount = data.getWalletData().getWallet_amount();
                                String pending_amount = data.getWalletData().getPending_amount();

                                AllDayAmount dayAmount = data.getAllDayAmount();
                                List<DayExpense> day_expense_list =dayAmount.getDayExpenses();

                                driverAmount = data;
                                allDayAmount = dayAmount;

                                activityBackTripBinding.dayExpenseLayout.approvedWalletAmount.setText(getString(R.string.approved_wallet_amount)+" - $"+
                                        data.getWalletData().getWallet_amount());

                                activityBackTripBinding.dayExpenseLayout.mainWalletAmount.setText(
                                        getString(R.string.main_wallet_amount_xtx)+" - $0");

                                et_wallet_amount.setText(getResources().getString(R.string.main_wallet_amount)+" : $"+wallet_amount);
                                activityBackTripBinding.dayExpenseLayout.giveBackCash.setText(getString(R.string.give_back_cash)+ " - $0");

                                if(pending_amount !=null){
                                    et_pending_amount.setText(getResources().getString(R.string.pending_amount)+" : $"+pending_amount);
                                }else{
                                    et_pending_amount.setText(getResources().getString(R.string.pending_amount)+" : $0");
                                }

                                et_approx_amount.setText(getResources().getString(R.string.approx_amount)+" : $"+approx_amount);

                                expenseAdapter = new AllDayExpenseAdapter(day_expense_list);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(expenseAdapter);

                                setTotalDayRecord(dayAmount,data);
                            }
                            hideProgressDialog();
                        }else{
                            hideProgressDialog();
                            Toast.makeText(BackTrip.this, "Try Later", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }catch (Exception e){
                        hideProgressDialog();
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ApproxAmountPay> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(BackTrip.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }

    private void setTotalDayRecord(AllDayAmount dayAmount,DriverAmount data){
        activityBackTripBinding.dayExpenseLayout.totalPayPerMile.setText(
                getString(R.string.total_pay_per_mile) +" : $"+dayAmount.getTotal_pay_per_mile());

        if(dayAmount.getTotal_day_rate()!=null){
            activityBackTripBinding.dayExpenseLayout.totalDayRate.setText(
                    getString(R.string.total_day_rate_pay) +" : $"+dayAmount.getTotal_day_rate());
        }else{
            activityBackTripBinding.dayExpenseLayout.totalDayRate.setText(
                    getString(R.string.total_day_rate_pay) +" : $0");
        }


        activityBackTripBinding.dayExpenseLayout.totalMiniPay.setText(
                getString(R.string.total_mini_pay) +" : $"+dayAmount.getTotal_mini_pay());

        activityBackTripBinding.dayExpenseLayout.mainTotalDownTimePriceTxt.setText(
                getString(R.string.total_down_time_pay) +" : $"+dayAmount.getMain_total_down_time_price());

        activityBackTripBinding.dayExpenseLayout.totalMotelExpenseCard.setText(
                getString(R.string.total_motel_expense_card) +" : $"+dayAmount.getTotal_motel_expense_card());

        activityBackTripBinding.dayExpenseLayout.totalMotelExpenseCash.setText(
                getString(R.string.total_motel_expense_cash) +" : $"+dayAmount.getTotal_motel_expense_cash());

        activityBackTripBinding.dayExpenseLayout.totalGasExpenseCard.setText(
                getString(R.string.total_gas_expense_card) +" : $"+dayAmount.getTotal_gas_expense_card());

        activityBackTripBinding.dayExpenseLayout.totalGasExpenseCash.setText(
                getString(R.string.total_gas_expense_cash) +" : $"+dayAmount.getTotal_gas_expense_cash());

        activityBackTripBinding.dayExpenseLayout.totalOtherExpenseCard.setText(
                getString(R.string.total_other_expense_card) +" : $"+dayAmount.getTotal_other_expense_card());

        activityBackTripBinding.dayExpenseLayout.totalOtherExpenseCash.setText(
                getString(R.string.total_other_expense_cash) +" : $"+dayAmount.getTotal_other_expense_cash());


        if(dayAmount.getMain_extra_pay_to_driver()!=null){
            activityBackTripBinding.dayExpenseLayout.mainExtraPayToDriverTxt.setText(
                    getString(R.string.main_extra_pay_to_driver) +" : $"+dayAmount.getMain_extra_pay_to_driver());
        }else{
            activityBackTripBinding.dayExpenseLayout.mainExtraPayToDriverTxt.setText(
                    getString(R.string.main_extra_pay_to_driver) +" : $0");
        }


        activityBackTripBinding.dayExpenseLayout.totalPayToDriverTxt.setText(
                getString(R.string.total_pay_to_driver) +" : $"+dayAmount.getTotal_pay_to_driver());

        activityBackTripBinding.dayExpenseLayout.totalPayDriver.setText(
                getString(R.string.total_amount_pay_to_driver) +" : $0");

        if(dayAmount.getMain_extra_pay_to_driver()!=null){
            total_pay_driver=dayAmount.getMain_extra_pay_to_driver();
        }else{
            total_pay_driver="0";
        }

        activityBackTripBinding.dayPrice.perMileCost.setText(getString(R.string.per_mile)+" - $"+
                dayAmount.getJob_pay_per_mile());
        activityBackTripBinding.dayPrice.jobDayRate.setText(getString(R.string.day_rate)+ " - $"+
                dayAmount.getJob_day_rate());
        activityBackTripBinding.dayPrice.jobNoGoesPrice.setText(getString(R.string.no_go_price) +
                " - $ "+dayAmount.getJob_no_go_price());

    }

    private boolean validateAmount(DriverAmount data,AllDayAmount allDayAmount){
      float int_advance_owned;
      float int_advance_deducted;
      float int_pay_to_driver;
      float main_wallet_amount;

         if(!data.getWalletData().getWallet_amount().isEmpty() &&
                 data.getWalletData().getWallet_amount()!=null){
             activityBackTripBinding.dayExpenseLayout.approvedWalletAmount.setText(getString(R.string.approved_wallet_amount)+" - $"+
                     data.getWalletData().getWallet_amount());
             main_wallet_amount = Integer.parseInt(data.getWalletData().getWallet_amount());
         }else{
             activityBackTripBinding.dayExpenseLayout.approvedWalletAmount.setText(getString(R.string.approved_wallet_amount)+" - $0");
             main_wallet_amount = 0;
         }

        String amount = activityBackTripBinding.advanceLayout.cashAmountEdittext.getText().toString();

        activityBackTripBinding.dayExpenseLayout.giveBackCash.setText(getString(R.string.give_back_cash)+ " - $"+amount);

        String advance_owned = data.getWalletData().getWallet_amount();
        if(!advance_owned.isEmpty() && advance_owned !=null){
            int_advance_owned = Float.parseFloat(advance_owned);
        }else{
            int_advance_owned = 0;
        }

        if(!amount.isEmpty() && amount !=null){
            int_advance_deducted = Float.parseFloat(amount);
        }else{
            int_advance_deducted = 0;
        }

        float left_to_pay = int_advance_owned - int_advance_deducted;

        if(!allDayAmount.getTotal_pay_to_driver().isEmpty() && allDayAmount.getTotal_pay_to_driver() !=null){
            int_pay_to_driver =  Float.parseFloat(allDayAmount.getTotal_pay_to_driver());
        }else{
            int_pay_to_driver = 0;
        }

       float amountPayDriver = int_pay_to_driver - int_advance_deducted;


        if(int_advance_deducted<=main_wallet_amount && int_advance_deducted<=int_pay_to_driver){

            activityBackTripBinding.dayExpenseLayout.mainWalletAmount.setText(
                    getString(R.string.main_wallet_amount_xtx)+" - $"+String.valueOf(left_to_pay));

            activityBackTripBinding.dayExpenseLayout.totalPayDriver.setText(
                    getString(R.string.total_amount_pay_to_driver) +" : $"+amountPayDriver);
            return  true;
        }else{

            Toast.makeText(this, getString(R.string.validate_message), Toast.LENGTH_LONG).show();

            activityBackTripBinding.dayExpenseLayout.mainWalletAmount.setText(
                    getString(R.string.main_wallet_amount_xtx)+" - $0");

            activityBackTripBinding.dayExpenseLayout.totalPayDriver.setText(
                    getString(R.string.total_amount_pay_to_driver) +" : $0");
            return false;
        }


    }

    private void resetView(){
        activityBackTripBinding.dayExpenseLayout.mainWalletAmount.setText(
                getString(R.string.main_wallet_amount_xtx)+" - $0");
        activityBackTripBinding.dayExpenseLayout.giveBackCash.setText(getString(R.string.give_back_cash)+ " - $0");
        activityBackTripBinding.dayExpenseLayout.totalPayDriver.setText(
                getString(R.string.total_amount_pay_to_driver) +" : $0");
    }
}
