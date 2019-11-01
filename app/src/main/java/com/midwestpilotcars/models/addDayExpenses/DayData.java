package com.midwestpilotcars.models.addDayExpenses;

import android.support.v4.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayData {
    @SerializedName("job_id")
    private String job_id;
    @SerializedName("current_day_expense_id")
    private String day_expense_id;
    @SerializedName("no_of_down_time_hours")
    private String down_time_hour;
    @SerializedName("no_of_down_time_hours_comment")
    private String down_time_comment;
    @SerializedName("no_of_miles_travelled")
    private String miles_travelled;
    @SerializedName("no_of_miles_travelled_comment")
    private String miles_travelled_commment;
    @SerializedName("no_go_day")
    private String no_go_day;
    @SerializedName("no_go_day_comment")
    private String go_day_comment;
    @SerializedName("choose_costing_from")
    private String costing_form;
    @SerializedName("mini_amount")
    private String mini_amount;
    @SerializedName("other_expense")
    private List<OtherExpense> other_expense;
    @SerializedName("gas_expense")
    private List<GasExpense> gas_expense;
    @SerializedName("motel_expense")
    private List<MotelExpense> motel_expense;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getDay_expense_id() {
        return day_expense_id;
    }

    public void setDay_expense_id(String day_expense_id) {
        this.day_expense_id = day_expense_id;
    }

    public String getDown_time_hour() {
        return down_time_hour;
    }

    public void setDown_time_hour(String down_time_hour) {
        this.down_time_hour = down_time_hour;
    }

    public String getDown_time_comment() {
        return down_time_comment;
    }

    public void setDown_time_comment(String down_time_comment) {
        this.down_time_comment = down_time_comment;
    }

    public String getMiles_travelled() {
        return miles_travelled;
    }

    public void setMiles_travelled(String miles_travelled) {
        this.miles_travelled = miles_travelled;
    }

    public String getMiles_travelled_commment() {
        return miles_travelled_commment;
    }

    public void setMiles_travelled_commment(String miles_travelled_commment) {
        this.miles_travelled_commment = miles_travelled_commment;
    }

    public String getNo_go_day() {
        return no_go_day;
    }

    public void setNo_go_day(String no_go_day) {
        this.no_go_day = no_go_day;
    }

    public String getGo_day_comment() {
        return go_day_comment;
    }

    public void setGo_day_comment(String go_day_comment) {
        this.go_day_comment = go_day_comment;
    }

    public String getCosting_form() {
        return costing_form;
    }

    public void setCosting_form(String costing_form) {
        this.costing_form = costing_form;
    }

    public String getMini_amount() {
        return mini_amount;
    }

    public void setMini_amount(String mini_amount) {
        this.mini_amount = mini_amount;
    }

    public List<OtherExpense> getOther_expense() {
        return other_expense;
    }

    public void setOther_expense(List<OtherExpense> other_expense) {
        this.other_expense = other_expense;
    }

    public List<GasExpense> getGas_expense() {
        return gas_expense;
    }

    public void setGas_expense(List<GasExpense> gas_expense) {
        this.gas_expense = gas_expense;
    }

    public List<MotelExpense> getMotel_expense() {
        return motel_expense;
    }

    public void setMotel_expense(List<MotelExpense> motel_expense) {
        this.motel_expense = motel_expense;
    }
}
