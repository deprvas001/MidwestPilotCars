package com.midwestpilotcars.views.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.midwestpilotcars.R;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.databinding.ActivityLoginBinding;
import com.midwestpilotcars.views.forgotPassword.ForgotPassword;
import com.midwestpilotcars.views.home.MainActivity;
import com.midwestpilotcars.views.splash.SplashViewModel;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        setClickListeners();
        setEditTextListeners();
    }

    private void setEditTextListeners() {
        activityLoginBinding.etUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityLoginBinding.textUserIdError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityLoginBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activityLoginBinding.textPasswordError.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setClickListeners() {
        activityLoginBinding.btnLogin.setOnClickListener(this);
        activityLoginBinding.tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkLoginCredentials();
                break;
            case R.id.tv_forgot_password:
                goToForgotPassword();
                break;

        }
    }

    private void checkLoginCredentials() {
        if (TextUtils.isEmpty(activityLoginBinding.etUserId.getText())) {
            activityLoginBinding.textUserIdError.setText(getString(R.string.error_field_empty));
        } else if (TextUtils.isEmpty(activityLoginBinding.etPassword.getText())) {
            activityLoginBinding.textPasswordError.setText(getString(R.string.error_field_empty));
        } else {
            showProgressDialog(getString(R.string.progress_log_in));
            //LoginViewModel class has a method loginUser
            loginViewModel.loginUser(this, activityLoginBinding.etUserId.getText().toString(), activityLoginBinding.etPassword.getText().toString()).observe(this, aBoolean -> {
                hideProgressDialog();
                if (aBoolean != null && aBoolean) {
                    getDefaultData();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_unsuccessful), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getDefaultData() {
        SplashViewModel splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.getDefaultData(this).observe(this, commonResponse -> {
            if (commonResponse.isStatus()) {
                Toast.makeText(LoginActivity.this, getString(R.string.login_successful), Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                if (commonResponse.getData() != null) {
                    Exception ex = (Exception) commonResponse.getData();
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToForgotPassword() {
        startActivity(new Intent(this, ForgotPassword.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
