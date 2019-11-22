package com.midwestpilotcars.helpers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.midwestpilotcars.MidwestPilotCars;
import com.midwestpilotcars.R;
import com.midwestpilotcars.base.BaseActivity;
import com.midwestpilotcars.constants.AppConstants;
import com.midwestpilotcars.models.allDefaultModels.LOADTYPE;
import com.midwestpilotcars.models.allDefaultModels.TRUCKINGCOMPANY;
import com.midwestpilotcars.views.customViews.DefaultDataDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class DialogUtils {
    public static void getCurrentDate(Context context) {
        Calendar mcurrentDate = Calendar.getInstance();
       // mcurrentDate.setTimeInMillis(System.currentTimeMillis() - 1000);
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener = (DatePickerDialog.OnDateSetListener) context;

        DatePickerDialog mDate = new DatePickerDialog(context, dateSetListener, mYear, mMonth, mDay);
        mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
       // mDate.getDatePicker().setMaxDate(System.currentTimeMillis() );
        mDate.show();
    }

    public static void showDefaultDataDialog(BaseActivity context, int dialogType) {
        DefaultDataDialog dialogFragment = new DefaultDataDialog();
        dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        Bundle bundle = new Bundle();

        if (dialogType == AppConstants.LOAD_TYPE)
            bundle.putParcelableArrayList(AppConstants.DEFAULT_DATA, SharedPreferenceHelper.Companion.getInstance().getDefaultData(context).getData().getLOADTYPE());
        else if (dialogType == AppConstants.TRUCK_COMPANY)
            bundle.putParcelableArrayList(AppConstants.DEFAULT_DATA, SharedPreferenceHelper.Companion.getInstance().getDefaultData(context).getData().getTRUCKINGCOMPANY());

        bundle.putInt(AppConstants.DEFAULT_DATA_TYPE, dialogType);
        dialogFragment.setArguments(bundle);

        FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
        Fragment prev = context.getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");
    }

    public static void hideDefaultDataDialog(BaseActivity context) {
        FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
        DefaultDataDialog dialogFragment = (DefaultDataDialog) context.getSupportFragmentManager().findFragmentByTag("dialog");
        if (dialogFragment != null) {
            ft.remove(dialogFragment).commit();
        }
        dialogFragment.dismiss();
    }

    public static void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    builder.create().dismiss();
                });
        builder.create().show();
    }

    public static void showNoInternetDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.app_name))
                .setMessage(context.getString(R.string.no_internet))
                .setCancelable(false)
                .setPositiveButton("Ok", onClickListener::onClick);
        builder.create().show();
    }


}
