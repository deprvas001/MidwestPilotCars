package com.midwestpilotcars.views.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.AllWalletAdapter;
import com.midwestpilotcars.adapters.DayWiseAdapter;
import com.midwestpilotcars.apiInterface.CompleteJobApi;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.databinding.ActivityForgotPasswordBinding;
import com.midwestpilotcars.databinding.ActivityWalletScreenBinding;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.helpers.Utils;
import com.midwestpilotcars.models.StatusAddDayExpense;
import com.midwestpilotcars.models.addDayExpenses.DayData;
import com.midwestpilotcars.models.addDayExpenses.DayExpenseData;
import com.midwestpilotcars.models.addDayExpenses.DayExperPerDay;
import com.midwestpilotcars.models.addDayExpenses.DayWiseTravell;
import com.midwestpilotcars.models.completejob.StatusCompleteJob;
import com.midwestpilotcars.models.getAllJobs.Data;
import com.midwestpilotcars.models.moneyRequest.MoneyRequestDetail;
import com.midwestpilotcars.models.wallet.AllMoneyRequest;
import com.midwestpilotcars.models.wallet.WalletAllDetail;
import com.midwestpilotcars.models.wallet.WalletData;
import com.midwestpilotcars.models.wallet.WalletModel;
import com.midwestpilotcars.views.splash.SplashActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalletScreen extends BaseActivity implements View.OnClickListener {
    ActivityWalletScreenBinding walletScreenBinding;
    AllWalletAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_wallet_screen);
        getSupportActionBar().setTitle(getString(R.string.title_wallet));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setClickListeners();

        if (Utils.Companion.isNetworkAvailable(this))
            getDriverWallet();
        else
            DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   WalletScreen.this.finish();
                }
            });
    }

    private void setClickListeners() {
        walletScreenBinding.btnLogin.setOnClickListener(this);
        walletScreenBinding.textView4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                moneyRequest();
                break;

            case R.id.textView4:
               viewVisibility();
                break;
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

    public void moneyRequest(){
        if (walletScreenBinding.etRequireAmount.getText().toString().isEmpty()) {
            walletScreenBinding.etRequireAmount.setError(getString(R.string.enter_amount_require));
        }else{
            sendMoneyRequest();
           /* if (Utils.Companion.isNetworkAvailable(this))
                sendMoneyRequest();
            else
                DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WalletScreen.this.finish();
                    }
                });*/
        }
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

                Call<WalletModel> call = api.getDriverWallet(token);

                call.enqueue(new Callback<WalletModel>() {
                    @Override
                    public void onResponse(Call<WalletModel> call, Response<WalletModel> response) {
                        try{
                            WalletModel status = response.body();

                            if (status.getStatus().equals("1")) {
                                if (status.getMessage().equals(getResources().getString(R.string.get_wallet))) {
                                    WalletData data = status.getData();
                                    String wallet_amount = data.getWallet_amount();
                                    if(!wallet_amount.equals("null")){
                                        walletScreenBinding.textView2.setText("$"+wallet_amount);
                                    }else {
                                        walletScreenBinding.textView2.setText("0");
                                    }
                                    if(data.getApprove_amount() !=null){
                                        walletScreenBinding.approvedAmount.setText("Approved Wallet Amount- "+"$"+data.getApprove_amount());
                                    }else{
                                        walletScreenBinding.approvedAmount.setText("Approved Wallet Amount- $0");
                                    }

                                    if(data.getPending_amount() !=null){
                                        walletScreenBinding.pendingAmount.setText("Pending Wallet Amount- "+"$"+data.getPending_amount());
                                    }else {
                                        walletScreenBinding.pendingAmount.setText("Pending Wallet Amount- $0");
                                    }

                                    if(data.getCash_advance() !=null){
                                        walletScreenBinding.cashAdvanceAmount.setText("Cash In Advance- "+"$"+data.getCash_advance());
                                    }else{
                                        walletScreenBinding.cashAdvanceAmount.setText("Cash In Advance- $0");
                                    }

                                    if(data.getCash_back() !=null) {
                                        walletScreenBinding.cashBack.setText("Cash Back- " +"$"+data.getCash_back());
                                    }else{
                                        walletScreenBinding.cashBack.setText("Cash Back- $0");
                                    }

                                    hideProgressDialog();
                                } else if (status.getMessage().equals(getResources().getString(R.string.job_complete_done))) {
                                    hideProgressDialog();
                                    Toast.makeText(WalletScreen.this, "Try Later", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }else{
                                hideProgressDialog();
                                Toast.makeText(WalletScreen.this, "Try Later", Toast.LENGTH_SHORT).show();
                                finish();
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
                    public void onFailure(Call<WalletModel> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(WalletScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                hideProgressDialog();
                e.printStackTrace();
            }

    }

    public void viewVisibility(){
        if(walletScreenBinding.detailWallet.getVisibility() == View.VISIBLE){
            walletScreenBinding.detailWallet.setVisibility(View.GONE);
        }else{
            walletScreenBinding.detailWallet.setVisibility(View.VISIBLE);
            getAllWallet();
           /* if (Utils.Companion.isNetworkAvailable(this))
                getAllWallet();
            else
                DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WalletScreen.this.finish();
                    }
                });*/
        }
    }

    public void sendMoneyRequest(){
        try {
            showProgressDialog(getString(R.string.loading));
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CompleteJobApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // create parameter with HashMap
            Map<String, String> params = new HashMap<>();
            params.put("wallet_amount", walletScreenBinding.etRequireAmount.getText().toString());

            CompleteJobApi api = retrofit.create(CompleteJobApi.class);

            Call<MoneyRequestDetail> call = api.requestedMoney(token,params);

            call.enqueue(new Callback<MoneyRequestDetail>() {
                @Override
                public void onResponse(Call<MoneyRequestDetail> call, Response<MoneyRequestDetail> response) {
                    try{
                        MoneyRequestDetail status = response.body();

                        if (status.getStatus().equals("1")) {
                            if (status.getMessage().equals(getResources().getString(R.string.wallet_amount_success))) {
                                hideProgressDialog();
                                Toast.makeText(WalletScreen.this, status.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (status.getMessage().equals(getResources().getString(R.string.job_complete_done))) {
                                hideProgressDialog();
                                Toast.makeText(WalletScreen.this, "Try Later", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            hideProgressDialog();
                            Toast.makeText(WalletScreen.this, "Try Later", Toast.LENGTH_SHORT).show();
                            finish();
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
                public void onFailure(Call<MoneyRequestDetail> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(WalletScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getAllWallet(){
        try {
            showProgressDialog(getString(R.string.loading));
            String token = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getAuthenticateToken();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CompleteJobApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CompleteJobApi api = retrofit.create(CompleteJobApi.class);

            Call<WalletAllDetail> call = api.getAllWallet(token,"ALL");

            call.enqueue(new Callback<WalletAllDetail>() {
                @Override
                public void onResponse(Call<WalletAllDetail> call, Response<WalletAllDetail> response) {
                    try{
                        WalletAllDetail status = response.body();
                        List<AllMoneyRequest> data = status.getData();

                        if (status.getStatus().equals("1")) {
                            if (status.getMessage().equals(getResources().getString(R.string.get_wallet))) {
                                hideProgressDialog();
                                mAdapter = new AllWalletAdapter(data);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                walletScreenBinding.recyclerView.setLayoutManager(mLayoutManager);
                                walletScreenBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                walletScreenBinding.recyclerView.addItemDecoration(new DividerItemDecoration(WalletScreen.this, LinearLayoutManager.VERTICAL));
                                walletScreenBinding.recyclerView.setAdapter(mAdapter);
                            } else if (status.getMessage().equals(getResources().getString(R.string.job_complete_done))) {
                                hideProgressDialog();
                                Toast.makeText(WalletScreen.this, "Try Later", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else{
                            hideProgressDialog();
                            Toast.makeText(WalletScreen.this, "Try Later", Toast.LENGTH_SHORT).show();
                            finish();
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
                public void onFailure(Call<WalletAllDetail> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(WalletScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            hideProgressDialog();
            e.printStackTrace();
        }
    }
}
