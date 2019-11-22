package com.midwestpilotcars.models.wallet;

import com.google.gson.annotations.SerializedName;

public class DayExpense {
    @SerializedName("down_time_hours")
    private String down_time_hours;
    @SerializedName("day_expense_id")
    private String day_expense_id;
    @SerializedName("no_go_day")
    private String no_go_day;
    @SerializedName("choose_costing_from")
    private String choose_costing_from;
    @SerializedName("no_of_miles_travelled_comment")
    private String no_of_miles_travelled_comment;
    @SerializedName("no_of_miles_travelled")
    private String no_of_miles_travelled;
    @SerializedName("mini_amount")
    private String mini_amount;
    @SerializedName("give_back_cash")
    private String give_back_cash;
    @SerializedName("day_wise_day_rate")
    private String day_wise_day_rate;
    @SerializedName("day_wise_total_motel_expense")
    private String day_wise_total_motel_expense;
    @SerializedName("day_wise_total_gas_expense")
    private String day_wise_total_gas_expense;
    @SerializedName("day_wise_total_other_expense")
    private String day_wise_total_other_expense;

    public String getDown_time_hours() {
        return down_time_hours;
    }

    public void setDown_time_hours(String down_time_hours) {
        this.down_time_hours = down_time_hours;
    }

    public String getDay_expense_id() {
        return day_expense_id;
    }

    public void setDay_expense_id(String day_expense_id) {
        this.day_expense_id = day_expense_id;
    }

    public String getNo_go_day() {
        return no_go_day;
    }

    public void setNo_go_day(String no_go_day) {
        this.no_go_day = no_go_day;
    }

    public String getChoose_costing_from() {
        return choose_costing_from;
    }

    public void setChoose_costing_from(String choose_costing_from) {
        this.choose_costing_from = choose_costing_from;
    }

    public String getNo_of_miles_travelled_comment() {
        return no_of_miles_travelled_comment;
    }

    public void setNo_of_miles_travelled_comment(String no_of_miles_travelled_comment) {
        this.no_of_miles_travelled_comment = no_of_miles_travelled_comment;
    }

    public String getNo_of_miles_travelled() {
        return no_of_miles_travelled;
    }

    public void setNo_of_miles_travelled(String no_of_miles_travelled) {
        this.no_of_miles_travelled = no_of_miles_travelled;
    }

    public String getMini_amount() {
        return mini_amount;
    }

    public void setMini_amount(String mini_amount) {
        this.mini_amount = mini_amount;
    }

    public String getGive_back_cash() {
        return give_back_cash;
    }

    public void setGive_back_cash(String give_back_cash) {
        this.give_back_cash = give_back_cash;
    }

    public String getDay_wise_day_rate() {
        return day_wise_day_rate;
    }

    public void setDay_wise_day_rate(String day_wise_day_rate) {
        this.day_wise_day_rate = day_wise_day_rate;
    }

    public String getDay_wise_total_motel_expense() {
        return day_wise_total_motel_expense;
    }

    public void setDay_wise_total_motel_expense(String day_wise_total_motel_expense) {
        this.day_wise_total_motel_expense = day_wise_total_motel_expense;
    }

    public String getDay_wise_total_gas_expense() {
        return day_wise_total_gas_expense;
    }

    public void setDay_wise_total_gas_expense(String day_wise_total_gas_expense) {
        this.day_wise_total_gas_expense = day_wise_total_gas_expense;
    }

    public String getDay_wise_total_other_expense() {
        return day_wise_total_other_expense;
    }

    public void setDay_wise_total_other_expense(String day_wise_total_other_expense) {
        this.day_wise_total_other_expense = day_wise_total_other_expense;
    }
}
