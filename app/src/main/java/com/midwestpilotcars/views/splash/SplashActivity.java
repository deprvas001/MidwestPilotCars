package com.midwestpilotcars.views.splash;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    public final static int TAG_PERMISSION_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activityt);


        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            showLoginScreen();
        }

       /* if(!checkPermission(SplashActivity.this)) {
            requestPermission(SplashActivity.this,TAG_PERMISSION_CODE);
        }else{
            showLoginScreen();
        }*/
    /*    new Handler().postDelayed(() -> {
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
        }, 3000);*/
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


    public static boolean checkPermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            return false;

        }

    }

    public void showLoginScreen(){
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            showLoginScreen();
        }
    }
}
