package com.midwestpilotcars.views.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.midwestpilotcars.R;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.helpers.DialogUtils;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.helpers.Utils;
import com.midwestpilotcars.models.loginModels.LoginResponseModel;
import com.midwestpilotcars.views.home.MainActivity;
import com.midwestpilotcars.views.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activityt);
        new Handler().postDelayed(() -> {
            //Companion is final static class in Utils class check after decompile its kotlin
            if (Utils.Companion.isNetworkAvailable(this))
                checkScreenRedirection();
            else
                DialogUtils.showNoInternetDialog(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SplashActivity.this.finish();
                    }
                });
        }, 3000);
    }

    private void checkScreenRedirection() {
        //SharedPreferenceHelper class has Companion final static class which has method getInstance
        //Which return SharedPreferenceHelper instance then it call getUserData method call of SharedPreferenceHelper class
        //return LoginRespones model instance for check already login or not
        //First time it return LoginRespnonseModel instance null

        LoginResponseModel loginResponseModel = SharedPreferenceHelper.Companion.getInstance().getUserData(this);

        //For first time is null
        if (loginResponseModel == null)
            goToLogin();
        else if (loginResponseModel.getData().getAuthenticateToken() != null) {
            goToHome();
        }
    }

    private void goToHome() {
        getDefaultData();

    }

    private void getDefaultData() {
        SplashViewModel splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.getDefaultData(this).observe(this, commonResponse -> {
            if (commonResponse.isStatus()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            } else {
                if (commonResponse.getData() == null) {
                 //   Toast.makeText(this, getString(R.string.auth_failure), Toast.LENGTH_SHORT).show();
                    goToLogin();
                } else {
                    Exception ex = (Exception) commonResponse.getData();
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
