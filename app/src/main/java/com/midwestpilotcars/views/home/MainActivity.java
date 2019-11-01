package com.midwestpilotcars.views.home;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.midwestpilotcars.R;
import com.midwestpilotcars.adapters.SectionsPagerAdapter;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.databinding.ActivityMainBinding;
import com.midwestpilotcars.helpers.SharedPreferenceHelper;
import com.midwestpilotcars.models.getAllJobs.GetAllJobsModel;
import com.midwestpilotcars.models.getAllJobs.UPCOMING;
import com.midwestpilotcars.views.activities.SettingsActivity;
import com.midwestpilotcars.views.createJob.AddNewJobActivity;
import com.midwestpilotcars.views.fragments.PlaceHolderFragment;
import com.midwestpilotcars.views.login.LoginActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ActivityMainBinding activityMainBinding;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private HomeViewModel homeViewModel;
    private GetAllJobsModel getAllJobsModel;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.toolbar.setTitle(getString(R.string.title_home));
        setSupportActionBar(activityMainBinding.toolbar); // Setting/replace toolbar as the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_settings);

       /* getSupportActionBar().setTitle(getString(R.string.title_home));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_settings);*/

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        activityMainBinding.container.setAdapter(mSectionsPagerAdapter);
        activityMainBinding.container.addOnPageChangeListener(this);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(activityMainBinding.container);
        tabLayout.getTabAt(0).setText(getString(R.string.text_new_jobs));
        tabLayout.getTabAt(1).setText(getString(R.string.text_ongoing_jobs));
        tabLayout.getTabAt(2).setText(getString(R.string.text_completed_job));

        setClickListeners();
        getAllJobs();
    }

    private void getAllJobs() {
        showProgressDialog(getResources().getString(R.string.loading));
        String userType = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getUserType();
        String userId = SharedPreferenceHelper.Companion.getInstance().getUserData(this).getData().getUserId();
        homeViewModel.getAllJobs(this, userType, userId, AppConstants.JOBS_ALL, 0, 10).observe(this, commonResponse -> {
            if (commonResponse.isStatus()) {
                hideProgressDialog();
                getAllJobsModel = (GetAllJobsModel) commonResponse.getData();
                String tag = "android:switcher:" + R.id.container + ":" + 0;
                PlaceHolderFragment placeHolderFragment = (PlaceHolderFragment) getSupportFragmentManager().findFragmentByTag(tag);
                placeHolderFragment.setNewJobData(getAllJobsModel.getData().getUPCOMING());
            } else {
                if (commonResponse.getData() == null) {
                    hideProgressDialog();
                    Toast.makeText(this, getString(R.string.auth_failure), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                } else {
                    hideProgressDialog();
                    Exception ex = (Exception) commonResponse.getData();
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setClickListeners() {
        activityMainBinding.imgBtnAddJob.setOnClickListener(this);
        activityMainBinding.refresh.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showSettingsFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSettingsFragment() {
        startActivity(new Intent(this, SettingsActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_add_job: {
                startActivity(new Intent(this, AddNewJobActivity.class));
                break;
            }

            case R.id.refresh: {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        Log.e("Page Selected", String.valueOf(i));
        String tag = "android:switcher:" + R.id.container + ":" + i;
        PlaceHolderFragment placeHolderFragment = (PlaceHolderFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (i == 0)
            placeHolderFragment.setNewJobData(getAllJobsModel.getData().getUPCOMING());
        else if (i == 1)
            placeHolderFragment.setOnGoingData(getAllJobsModel.getData().getONGOING());
        else if (i == 2)
            placeHolderFragment.setCompleteData(getAllJobsModel.getData().getCOMPLETE());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        alertDialog();
    }

    public void alertDialog() {
        builder = new AlertDialog.Builder(this);
//Uncomment the below code to Set the message and title from the strings.xml file
        //    builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //logout();
                        finishAffinity();
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
        alert.setTitle("Close App");
        alert.show();

    }
}
