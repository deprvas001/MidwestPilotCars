
package com.midwestpilotcars.models.startJob;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class StartJobRequestModel {

    @SerializedName("job_detention_price")
    private String jobDetentionPrice;
    @SerializedName("job_detention_price_comment")
    private String jobDetentionPriceComment="";
    @SerializedName("job_id")
    private String jobId;
    @SerializedName("job_no_goes_price")
    private String jobNoGoesPrice;
    @SerializedName("job_no_goes_price_comment")
    private String jobNoGoesPriceComment="";
    @SerializedName("job_pay_per_mile_cost")
    private String jobPayPerMileCost;
    @SerializedName("job_pay_per_mile_cost_comment")
    private String jobPayPerMileCostComment="";
    @SerializedName("job_per_day_rate")
    private String jobPerDayRate;
    @SerializedName("job_per_day_rate_comment")
    private String jobPerDayRateComment="";
    @SerializedName("job_pilot_driver_start_location")
    private String jobPilotDriverStartLocation;
    @SerializedName("job_pilot_driver_start_location_latitude")
    private String jobPilotDriverStartLocationLatitude;
    @SerializedName("job_pilot_driver_start_location_longitude")
    private String jobPilotDriverStartLocationLongitude;
    @SerializedName("job_pilot_vehile_odometer_reading")
    private String jobPilotVehileOdometerReading;
    @SerializedName("job_pilot_vehile_odometer_reading_url")
    private String jobPilotVehileOdometerReadingUrl;
    @SerializedName("job_start_date_time")
    private String jobStartDateTime;
    @SerializedName("job_start_date_time_comment")
    private String jobStartDateTimeComment="";
    @SerializedName("job_truck_load_type_id")
    private String jobTruckLoadTypeId;

    @SerializedName("job_truck_weight")
    private String job_truck_weight;
    @SerializedName("job_truck_length")
    private String job_truck_length;
    @SerializedName("jab_dot_number")
    private String jabDotNumber;
    @SerializedName("job_load_description")
    private String jobLoadDescription;
    @SerializedName("job_load_number")
    private String jobLoadNumber;
    @SerializedName("job_truck_driver_name")
    private String jobTruckDriverName;
    @SerializedName("job_truck_driver_phone_number")
    private String jobTruckDriverPhoneNumber;
    @SerializedName("job_truck_height")
    private String jobTruckHeight;
    @SerializedName("job_truck_number")
    private String jobTruckNumber;
    @SerializedName("job_truck_width")
    private String jobTruckWidth;
    @SerializedName("job_cash_in_advance")
    private String jobCashInAdvance;

    public String getJobCashInAdvance() {
        return jobCashInAdvance;
    }

    public void setJobCashInAdvance(String jobCashInAdvance) {
        this.jobCashInAdvance = jobCashInAdvance;
    }

    public String getJob_truck_weight() {
        return job_truck_weight;
    }

    public void setJob_truck_weight(String job_truck_weight) {
        this.job_truck_weight = job_truck_weight;
    }

    public String getJob_truck_length() {
        return job_truck_length;
    }

    public void setJob_truck_length(String job_truck_length) {
        this.job_truck_length = job_truck_length;
    }

    public String getJabDotNumber() {
        return jabDotNumber;
    }

    public void setJabDotNumber(String jabDotNumber) {
        this.jabDotNumber = jabDotNumber;
    }

    public String getJobLoadDescription() {
        return jobLoadDescription;
    }

    public void setJobLoadDescription(String jobLoadDescription) {
        this.jobLoadDescription = jobLoadDescription;
    }

    public String getJobLoadNumber() {
        return jobLoadNumber;
    }

    public void setJobLoadNumber(String jobLoadNumber) {
        this.jobLoadNumber = jobLoadNumber;
    }

    public String getJobTruckDriverName() {
        return jobTruckDriverName;
    }

    public void setJobTruckDriverName(String jobTruckDriverName) {
        this.jobTruckDriverName = jobTruckDriverName;
    }

    public String getJobTruckDriverPhoneNumber() {
        return jobTruckDriverPhoneNumber;
    }

    public void setJobTruckDriverPhoneNumber(String jobTruckDriverPhoneNumber) {
        this.jobTruckDriverPhoneNumber = jobTruckDriverPhoneNumber;
    }

    public String getJobTruckHeight() {
        return jobTruckHeight;
    }

    public void setJobTruckHeight(String jobTruckHeight) {
        this.jobTruckHeight = jobTruckHeight;
    }

    public String getJobTruckNumber() {
        return jobTruckNumber;
    }

    public void setJobTruckNumber(String jobTruckNumber) {
        this.jobTruckNumber = jobTruckNumber;
    }

    public String getJobTruckWidth() {
        return jobTruckWidth;
    }

    public void setJobTruckWidth(String jobTruckWidth) {
        this.jobTruckWidth = jobTruckWidth;
    }

    public String getJobDetentionPrice() {
        return jobDetentionPrice;
    }

    public void setJobDetentionPrice(String jobDetentionPrice) {
        this.jobDetentionPrice = jobDetentionPrice;
    }

    public String getJobDetentionPriceComment() {
        return jobDetentionPriceComment;
    }

    public void setJobDetentionPriceComment(String jobDetentionPriceComment) {
        this.jobDetentionPriceComment = jobDetentionPriceComment;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobNoGoesPrice() {
        return jobNoGoesPrice;
    }

    public void setJobNoGoesPrice(String jobNoGoesPrice) {
        this.jobNoGoesPrice = jobNoGoesPrice;
    }

    public String getJobNoGoesPriceComment() {
        return jobNoGoesPriceComment;
    }

    public void setJobNoGoesPriceComment(String jobNoGoesPriceComment) {
        this.jobNoGoesPriceComment = jobNoGoesPriceComment;
    }

    public String getJobPayPerMileCost() {
        return jobPayPerMileCost;
    }

    public void setJobPayPerMileCost(String jobPayPerMileCost) {
        this.jobPayPerMileCost = jobPayPerMileCost;
    }

    public String getJobPayPerMileCostComment() {
        return jobPayPerMileCostComment;
    }

    public void setJobPayPerMileCostComment(String jobPayPerMileCostComment) {
        this.jobPayPerMileCostComment = jobPayPerMileCostComment;
    }

    public String getJobPerDayRate() {
        return jobPerDayRate;
    }

    public void setJobPerDayRate(String jobPerDayRate) {
        this.jobPerDayRate = jobPerDayRate;
    }

    public String getJobPerDayRateComment() {
        return jobPerDayRateComment;
    }

    public void setJobPerDayRateComment(String jobPerDayRateComment) {
        this.jobPerDayRateComment = jobPerDayRateComment;
    }

    public String getJobPilotDriverStartLocation() {
        return jobPilotDriverStartLocation;
    }

    public void setJobPilotDriverStartLocation(String jobPilotDriverStartLocation) {
        this.jobPilotDriverStartLocation = jobPilotDriverStartLocation;
    }

    public String getJobPilotDriverStartLocationLatitude() {
        return jobPilotDriverStartLocationLatitude;
    }

    public void setJobPilotDriverStartLocationLatitude(String jobPilotDriverStartLocationLatitude) {
        this.jobPilotDriverStartLocationLatitude = jobPilotDriverStartLocationLatitude;
    }

    public String getJobPilotDriverStartLocationLongitude() {
        return jobPilotDriverStartLocationLongitude;
    }

    public void setJobPilotDriverStartLocationLongitude(String jobPilotDriverStartLocationLongitude) {
        this.jobPilotDriverStartLocationLongitude = jobPilotDriverStartLocationLongitude;
    }

    public String getJobPilotVehileOdometerReading() {
        return jobPilotVehileOdometerReading;
    }

    public void setJobPilotVehileOdometerReading(String jobPilotVehileOdometerReading) {
        this.jobPilotVehileOdometerReading = jobPilotVehileOdometerReading;
    }

    public String getJobPilotVehileOdometerReadingUrl() {
        return jobPilotVehileOdometerReadingUrl;
    }

    public void setJobPilotVehileOdometerReadingUrl(String jobPilotVehileOdometerReadingUrl) {
        this.jobPilotVehileOdometerReadingUrl = jobPilotVehileOdometerReadingUrl;
    }

    public String getJobStartDateTime() {
        return jobStartDateTime;
    }

    public void setJobStartDateTime(String jobStartDateTime) {
        this.jobStartDateTime = jobStartDateTime;
    }

    public String getJobStartDateTimeComment() {
        return jobStartDateTimeComment;
    }

    public void setJobStartDateTimeComment(String jobStartDateTimeComment) {
        this.jobStartDateTimeComment = jobStartDateTimeComment;
    }

    public String getJobTruckLoadTypeId() {
        return jobTruckLoadTypeId;
    }

    public void setJobTruckLoadTypeId(String jobTruckLoadTypeId) {
        this.jobTruckLoadTypeId = jobTruckLoadTypeId;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
