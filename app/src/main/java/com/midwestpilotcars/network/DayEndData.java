package com.midwestpilotcars.network;

import android.content.Context;
import android.widget.Toast;

import com.midwestpilotcars.models.addDayExpenses.DayGasExpense;
import com.midwestpilotcars.models.addDayExpenses.DayMotelExpense;
import com.midwestpilotcars.models.addDayExpenses.OtherExpenseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DayEndData {
    public static String getResult(Context context,String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if (status.equals("1")) {
                if (message.equals("successfully your day job is done")) {

                    JSONObject data = jsonObject.getJSONObject("data");
                    String job_id = data.getString("job_id");
                    String trip_status = data.getString("end_trip_status");


                }
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
        return "";

    }
}
