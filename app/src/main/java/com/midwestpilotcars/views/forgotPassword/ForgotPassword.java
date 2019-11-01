package com.midwestpilotcars.views.forgotPassword;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.midwestpilotcars.R;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.databinding.ActivityForgotPasswordBinding;
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordRequestModel;
import com.midwestpilotcars.models.forgotPassword.ForgotPasswordResponseModel;

public class ForgotPassword extends BaseActivity implements View.OnClickListener {
    private ActivityForgotPasswordBinding forgotPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forgotPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        getSupportActionBar().setTitle(getString(R.string.title_forgot_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setClickListeners();
    }

    private void setClickListeners() {
        forgotPasswordBinding.btnLogin.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                recoverPassword();
                break;
        }
    }

    private void recoverPassword() {

        ForgotPasswordViewModel forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        ForgotPasswordRequestModel forgotPasswordRequestModel = new ForgotPasswordRequestModel();
        if (forgotPasswordBinding.etEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_email_address), Toast.LENGTH_LONG).show();

        } else {
            makeApiCall(true);
            forgotPasswordRequestModel.setUserName(forgotPasswordBinding.etEmail.getText().toString());
            forgotPasswordViewModel.recoverPassword(forgotPasswordRequestModel).observe(this, commonResponse -> {
                if (commonResponse.isStatus()) {
                    makeApiCall(false);
                    ForgotPasswordResponseModel forgotPasswordResponseModel = (ForgotPasswordResponseModel) commonResponse.getData();
                    Toast.makeText(this, forgotPasswordResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    makeApiCall(false);
                    Toast.makeText(this, getString(R.string.user_name_not_exist), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void makeApiCall(boolean b) {
        if (b) {
            forgotPasswordBinding.progressBar.setVisibility(View.VISIBLE);
            forgotPasswordBinding.btnLogin.setClickable(false);
        }else{
            forgotPasswordBinding.progressBar.setVisibility(View.GONE);
            forgotPasswordBinding.btnLogin.setClickable(true);
        }
    }
}
