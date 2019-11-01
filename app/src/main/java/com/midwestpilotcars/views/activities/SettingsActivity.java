package com.midwestpilotcars.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.midwestpilotcars.R;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.databinding.ActivitySettingsBinding;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.views.resetPassword.ResetPassword;
import com.midwestpilotcars.views.login.LoginActivity;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySettingsBinding activitySettingsBinding;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        setClickListners();
    }

    private void setClickListners() {
        activitySettingsBinding.btnCloseSettings.setOnClickListener(this);
        activitySettingsBinding.textChangePassword.setOnClickListener(this);
        activitySettingsBinding.textLogOut.setOnClickListener(this);
        activitySettingsBinding.textWallet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close_settings:
                closeSettings();
                break;
            case R.id.text_change_password:
                startChangePassword();
                break;

            case R.id.text_wallet:
                startWallet();
                break;

            case R.id.text_log_out:
                alertDialog();
                break;
        }
    }

    private void logout() {
        SharedPreferenceHelper.Companion.getInstance().clearUserData(this);
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void startChangePassword() {
        startActivity(new Intent(this, ResetPassword.class));
        finish();
    }

    private void closeSettings() {
        this.finish();
    }

    public void alertDialog() {
        builder = new AlertDialog.Builder(this);
//Uncomment the below code to Set the message and title from the strings.xml file
        //    builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        logout();
                        // finish();
                      /*  Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();*/
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        alert.setTitle("Log Out");
        alert.show();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public  void startWallet(){
        startActivity(new Intent(this, WalletScreen.class));
        finish();
    }
}
