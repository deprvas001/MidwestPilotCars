package com.midwestpilotcars.views.resetPassword;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.midwestpilotcars.R;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.databinding.ActivityResetPasswordBinding;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.createJob.AddJobResponseModel;
import com.midwestpilotcars.models.resetPassword.ResetPasswordRequestModel;
import com.midwestpilotcars.models.resetPassword.ResetPasswordResponseModel;

public class ResetPassword extends BaseActivity implements View.OnClickListener {
    private ActivityResetPasswordBinding resetPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        getSupportActionBar().setTitle(getString(R.string.title_reset_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupClickListeners();

    }

    private void setupClickListeners() {
        resetPasswordBinding.btnLogin.setOnClickListener(this);
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
                resetPassword();
                break;
        }
    }

    private void resetPassword() {
        if (resetPasswordBinding.etOldPassword.getText().toString().trim().isEmpty() || resetPasswordBinding.etNewPassword.getText().toString().trim().isEmpty() || resetPasswordBinding.etConfirmPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.error_field_empty), Toast.LENGTH_LONG).show();
        } else if (!resetPasswordBinding.etNewPassword.getText().toString().trim().equals(resetPasswordBinding.etConfirmPassword.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.password_not_match), Toast.LENGTH_LONG).show();
        } else {
            apiCallInProgress(true);
            ResetPasswordViewModel resetPasswordViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
            ResetPasswordRequestModel resetPasswordRequestModel = new ResetPasswordRequestModel();
            resetPasswordRequestModel.setUserName(SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getUserEmail());
            resetPasswordRequestModel.setOldPassword(resetPasswordBinding.etOldPassword.getText().toString().trim());
            resetPasswordRequestModel.setNewPassword(resetPasswordBinding.etNewPassword.getText().toString().trim());

            resetPasswordViewModel.resetPassword(this, resetPasswordRequestModel).observe(this, commonResponse -> {
                if (commonResponse.isStatus()) {
                    apiCallInProgress(false);
                    ResetPasswordResponseModel resetPasswordResponseModel = (ResetPasswordResponseModel) commonResponse.getData();
                    Toast.makeText(this, resetPasswordResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    if (commonResponse.getData() == null) {
                        Toast.makeText(this, getString(R.string.auth_failure), Toast.LENGTH_SHORT).show();
                    } else {
                        Exception ex = (Exception) commonResponse.getData();
                        if (ex.getMessage().contains("400")) {
                            Toast.makeText(this, getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    apiCallInProgress(false);
                }
            });
        }
    }

    private void apiCallInProgress(boolean b) {
        if (b) {
            resetPasswordBinding.progressBar.setVisibility(View.VISIBLE);
            resetPasswordBinding.btnLogin.setClickable(false);
        } else {
            resetPasswordBinding.progressBar.setVisibility(View.GONE);
            resetPasswordBinding.btnLogin.setClickable(true);
        }
    }
}
