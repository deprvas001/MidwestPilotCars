package com.midwestpilotcars.models.addDayExpenses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayAllExpense {
    private String miles_travlled_comment;
    private String go_day;
    private String down_time;
    private String down_time_comment;
    private String costing_form;
    private String miles_travelled;
    private String  job_id;
    private String go_day_comment;
    private String mini_amount;
    private String mini_comment;

    private String day_expense_id;
    private List<OtherExpenseModel> other_list;
    private List<DayGasExpense> gas_list;
    private List<DayMotelExpense> motel_list;

    public DayAllExpense(){

    }

    public String getMiles_travlled_comment() {
        return miles_travlled_comment;
    }

    public void setMiles_travlled_comment(String miles_travlled_comment) {
        this.miles_travlled_comment = miles_travlled_comment;
    }

    public String getGo_day() {
        return go_day;
    }

    public void setGo_day(String go_day) {
        this.go_day = go_day;
    }

    public String getDown_time() {
        return down_time;
    }

    public void setDown_time(String down_time) {
        this.down_time = down_time;
    }

    public String getDown_time_comment() {
        return down_time_comment;
    }

    public void setDown_time_comment(String down_time_comment) {
        this.down_time_comment = down_time_comment;
    }

    public String getCosting_form() {
        return costing_form;
    }

    public void setCosting_form(String costing_form) {
        this.costing_form = costing_form;
    }

    public String getMiles_travelled() {
        return miles_travelled;
    }

    public void setMiles_travelled(String miles_travelled) {
        this.miles_travelled = miles_travelled;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getGo_day_comment() {
        return go_day_comment;
    }

    public void setGo_day_comment(String go_day_comment) {
        this.go_day_comment = go_day_comment;
    }

    public String getMini_amount() {
        return mini_amount;
    }

    public void setMini_amount(String mini_amount) {
        this.mini_amount = mini_amount;
    }

    public String getMini_comment() {
        return mini_comment;
    }

    public void setMini_comment(String mini_comment) {
        this.mini_comment = mini_comment;
    }

    public String getDay_expense_id() {
        return day_expense_id;
    }

    public void setDay_expense_id(String day_expense_id) {
        this.day_expense_id = day_expense_id;
    }

    public List<OtherExpenseModel> getOther_list() {
        return other_list;
    }

    public void setOther_list(List<OtherExpenseModel> other_list) {
        this.other_list = other_list;
    }

    public List<DayGasExpense> getGas_list() {
        return gas_list;
    }

    public void setGas_list(List<DayGasExpense> gas_list) {
        this.gas_list = gas_list;
    }

    public List<DayMotelExpense> getMotel_list() {
        return motel_list;
    }

    public void setMotel_list(List<DayMotelExpense> motel_list) {
        this.motel_list = motel_list;
    }
}
