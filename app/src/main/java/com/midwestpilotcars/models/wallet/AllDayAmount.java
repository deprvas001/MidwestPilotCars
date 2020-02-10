package com.midwestpilotcars.models.wallet;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllDayAmount {
    @SerializedName("job_down_time_price_per_hour")
    private String job_down_time_price;
    @SerializedName("job_pay_per_mile_cost")
    private String job_pay_per_mile;
    @SerializedName("job_day_rate")
    private String job_day_rate;
    @SerializedName("job_no_goes_price")
    private String job_no_go_price;
    @SerializedName("job_detention_price")
    private String job_detention_price;
    @SerializedName("job_cash_in_advance")
    private String job_cash_in_advance;
    @SerializedName("job_extra_pay_to_driver")
    private String job_extra_pay_to_driver;
    private String total_day_rate;
    private String total_down_time_hours;
    private String total_motel_expense;
    private String total_gas_expense;
    private String total_other_expense;
    private String total_pay_per_mile;
    private String total_mini_pay;
    private String total_motel_expense_card;
    private String total_motel_expense_cash;
    private String total_gas_expense_card;
    private String total_gas_expense_cash;
    private String total_other_expense_card;
    private String total_other_expense_cash;
    private String main_wallet_amount;
    private String give_back_cash;
    private String total_day_no_goes_price;

    public String getTotal_day_no_goes_price() {
        return total_day_no_goes_price;
    }

    public void setTotal_day_no_goes_price(String total_day_no_goes_price) {
        this.total_day_no_goes_price = total_day_no_goes_price;
    }

    public String getTotal_motel_expense_card() {
        return total_motel_expense_card;
    }

    public void setTotal_motel_expense_card(String total_motel_expense_card) {
        this.total_motel_expense_card = total_motel_expense_card;
    }

    public String getTotal_motel_expense_cash() {
        return total_motel_expense_cash;
    }

    public void setTotal_motel_expense_cash(String total_motel_expense_cash) {
        this.total_motel_expense_cash = total_motel_expense_cash;
    }

    public String getTotal_gas_expense_card() {
        return total_gas_expense_card;
    }

    public void setTotal_gas_expense_card(String total_gas_expense_card) {
        this.total_gas_expense_card = total_gas_expense_card;
    }

    public String getTotal_gas_expense_cash() {
        return total_gas_expense_cash;
    }

    public void setTotal_gas_expense_cash(String total_gas_expense_cash) {
        this.total_gas_expense_cash = total_gas_expense_cash;
    }

    public String getTotal_other_expense_card() {
        return total_other_expense_card;
    }

    public void setTotal_other_expense_card(String total_other_expense_card) {
        this.total_other_expense_card = total_other_expense_card;
    }

    public String getTotal_other_expense_cash() {
        return total_other_expense_cash;
    }

    public void setTotal_other_expense_cash(String total_other_expense_cash) {
        this.total_other_expense_cash = total_other_expense_cash;
    }

    public String getMain_wallet_amount() {
        return main_wallet_amount;
    }

    public void setMain_wallet_amount(String main_wallet_amount) {
        this.main_wallet_amount = main_wallet_amount;
    }

    public String getGive_back_cash() {
        return give_back_cash;
    }

    public void setGive_back_cash(String give_back_cash) {
        this.give_back_cash = give_back_cash;
    }

    public String getTotal_mini_pay() {
        return total_mini_pay;
    }

    public void setTotal_mini_pay(String total_mini_pay) {
        this.total_mini_pay = total_mini_pay;
    }

    public String getTotal_day_rate() {
        return total_day_rate;
    }

    public void setTotal_day_rate(String total_day_rate) {
        this.total_day_rate = total_day_rate;
    }

    public String getTotal_down_time_hours() {
        return total_down_time_hours;
    }

    public void setTotal_down_time_hours(String total_down_time_hours) {
        this.total_down_time_hours = total_down_time_hours;
    }

    public String getTotal_motel_expense() {
        return total_motel_expense;
    }

    public void setTotal_motel_expense(String total_motel_expense) {
        this.total_motel_expense = total_motel_expense;
    }

    public String getTotal_gas_expense() {
        return total_gas_expense;
    }

    public void setTotal_gas_expense(String total_gas_expense) {
        this.total_gas_expense = total_gas_expense;
    }

    public String getTotal_other_expense() {
        return total_other_expense;
    }

    public void setTotal_other_expense(String total_other_expense) {
        this.total_other_expense = total_other_expense;
    }

    public String getMain_total_days_expense() {
        return main_total_days_expense;
    }

    public void setMain_total_days_expense(String main_total_days_expense) {
        this.main_total_days_expense = main_total_days_expense;
    }

    public String getMain_total_price() {
        return main_total_price;
    }

    public void setMain_total_price(String main_total_price) {
        this.main_total_price = main_total_price;
    }

    public String getMain_total_card_expense() {
        return main_total_card_expense;
    }

    public void setMain_total_card_expense(String main_total_card_expense) {
        this.main_total_card_expense = main_total_card_expense;
    }

    public String getMain_total_down_time_price() {
        return main_total_down_time_price;
    }

    public void setMain_total_down_time_price(String main_total_down_time_price) {
        this.main_total_down_time_price = main_total_down_time_price;
    }

    public String getMain_extra_pay_to_driver() {
        return main_extra_pay_to_driver;
    }

    public void setMain_extra_pay_to_driver(String main_extra_pay_to_driver) {
        this.main_extra_pay_to_driver = main_extra_pay_to_driver;
    }

    public String getTotal_pay_to_driver() {
        return total_pay_to_driver;
    }

    public void setTotal_pay_to_driver(String total_pay_to_driver) {
        this.total_pay_to_driver = total_pay_to_driver;
    }

    private String main_total_days_expense;
    private String main_total_price;
    private String main_total_card_expense;
    private String main_total_down_time_price;
    private String main_extra_pay_to_driver;
    private String total_pay_to_driver;

    @SerializedName("days")
    private List<DayExpense> dayExpenses;

    public String getJob_down_time_price() {
        return job_down_time_price;
    }

    public void setJob_down_time_price(String job_down_time_price) {
        this.job_down_time_price = job_down_time_price;
    }

    public String getJob_pay_per_mile() {
        return job_pay_per_mile;
    }

    public void setJob_pay_per_mile(String job_pay_per_mile) {
        this.job_pay_per_mile = job_pay_per_mile;
    }

    public String getJob_day_rate() {
        return job_day_rate;
    }

    public void setJob_day_rate(String job_day_rate) {
        this.job_day_rate = job_day_rate;
    }

    public String getJob_no_go_price() {
        return job_no_go_price;
    }

    public void setJob_no_go_price(String job_no_go_price) {
        this.job_no_go_price = job_no_go_price;
    }

    public String getJob_detention_price() {
        return job_detention_price;
    }

    public void setJob_detention_price(String job_detention_price) {
        this.job_detention_price = job_detention_price;
    }

    public String getJob_cash_in_advance() {
        return job_cash_in_advance;
    }

    public void setJob_cash_in_advance(String job_cash_in_advance) {
        this.job_cash_in_advance = job_cash_in_advance;
    }

    public String getJob_extra_pay_to_driver() {
        return job_extra_pay_to_driver;
    }

    public void setJob_extra_pay_to_driver(String job_extra_pay_to_driver) {
        this.job_extra_pay_to_driver = job_extra_pay_to_driver;
    }

    public String getTotal_pay_per_mile() {
        return total_pay_per_mile;
    }

    public void setTotal_pay_per_mile(String total_pay_per_mile) {
        this.total_pay_per_mile = total_pay_per_mile;
    }

    public List<DayExpense> getDayExpenses() {
        return dayExpenses;
    }

    public void setDayExpenses(List<DayExpense> dayExpenses) {
        this.dayExpenses = dayExpenses;
    }
}
