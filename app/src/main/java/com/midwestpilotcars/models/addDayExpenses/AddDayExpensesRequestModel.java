
package com.midwestpilotcars.models.addDayExpenses;

import com.google.gson.annotations.SerializedName;

public class AddDayExpensesRequestModel {

    @SerializedName("choose_costing_from")
    private Long chooseCostingFrom;
    @SerializedName("gas_expense_comment")
    private String gasExpenseComment;
    @SerializedName("gas_expense_image")
    private String gasExpenseImage;
    @SerializedName("gas_expense_mode")
    private Long gasExpenseMode;
    @SerializedName("gas_expense_price")
    private String gasExpensePrice;
    @SerializedName("job_id")
    private Long jobId;
    @SerializedName("motel_expense_comment")
    private String motelExpenseComment;
    @SerializedName("motel_expense_image")
    private String motelExpenseImage;
    @SerializedName("motel_expense_mode")
    private Long motelExpenseMode;
    @SerializedName("motel_expense_price")
    private String motelExpensePrice;
    @SerializedName("no_go_day")
    private Long noGoDay;
    @SerializedName("no_of_down_time_hours")
    private String noOfDownTimeHours;
    @SerializedName("no_of_down_time_hours_comment")
    private String noOfDownTimeHoursComment;
    @SerializedName("no_of_miles_travelled")
    private String noOfMilesTravelled;
    @SerializedName("no_of_miles_travelled_comment")
    private String noOfMilesTravelledComment;
    @SerializedName("other_expense")
    private String otherExpense;

    public Long getChooseCostingFrom() {
        return chooseCostingFrom;
    }

    public void setChooseCostingFrom(Long chooseCostingFrom) {
        this.chooseCostingFrom = chooseCostingFrom;
    }

    public String getGasExpenseComment() {
        return gasExpenseComment;
    }

    public void setGasExpenseComment(String gasExpenseComment) {
        this.gasExpenseComment = gasExpenseComment;
    }

    public String getGasExpenseImage() {
        return gasExpenseImage;
    }

    public void setGasExpenseImage(String gasExpenseImage) {
        this.gasExpenseImage = gasExpenseImage;
    }

    public Long getGasExpenseMode() {
        return gasExpenseMode;
    }

    public void setGasExpenseMode(Long gasExpenseMode) {
        this.gasExpenseMode = gasExpenseMode;
    }

    public String getGasExpensePrice() {
        return gasExpensePrice;
    }

    public void setGasExpensePrice(String gasExpensePrice) {
        this.gasExpensePrice = gasExpensePrice;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getMotelExpenseComment() {
        return motelExpenseComment;
    }

    public void setMotelExpenseComment(String motelExpenseComment) {
        this.motelExpenseComment = motelExpenseComment;
    }

    public String getMotelExpenseImage() {
        return motelExpenseImage;
    }

    public void setMotelExpenseImage(String motelExpenseImage) {
        this.motelExpenseImage = motelExpenseImage;
    }

    public Long getMotelExpenseMode() {
        return motelExpenseMode;
    }

    public void setMotelExpenseMode(Long motelExpenseMode) {
        this.motelExpenseMode = motelExpenseMode;
    }

    public String getMotelExpensePrice() {
        return motelExpensePrice;
    }

    public void setMotelExpensePrice(String motelExpensePrice) {
        this.motelExpensePrice = motelExpensePrice;
    }

    public Long getNoGoDay() {
        return noGoDay;
    }

    public void setNoGoDay(Long noGoDay) {
        this.noGoDay = noGoDay;
    }

    public String getNoOfDownTimeHours() {
        return noOfDownTimeHours;
    }

    public void setNoOfDownTimeHours(String noOfDownTimeHours) {
        this.noOfDownTimeHours = noOfDownTimeHours;
    }

    public String getNoOfDownTimeHoursComment() {
        return noOfDownTimeHoursComment;
    }

    public void setNoOfDownTimeHoursComment(String noOfDownTimeHoursComment) {
        this.noOfDownTimeHoursComment = noOfDownTimeHoursComment;
    }

    public String getNoOfMilesTravelled() {
        return noOfMilesTravelled;
    }

    public void setNoOfMilesTravelled(String noOfMilesTravelled) {
        this.noOfMilesTravelled = noOfMilesTravelled;
    }

    public String getNoOfMilesTravelledComment() {
        return noOfMilesTravelledComment;
    }

    public void setNoOfMilesTravelledComment(String noOfMilesTravelledComment) {
        this.noOfMilesTravelledComment = noOfMilesTravelledComment;
    }

    public String getOtherExpense() {
        return otherExpense;
    }

    public void setOtherExpense(String otherExpense) {
        this.otherExpense = otherExpense;
    }

}
