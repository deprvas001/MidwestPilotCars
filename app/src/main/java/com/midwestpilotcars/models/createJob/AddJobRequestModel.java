
package com.midwestpilotcars.models.createJob;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class AddJobRequestModel {

    @SerializedName("jab_dot_number")
    private String jobDotNumber;
    @SerializedName("job_assign_trucking_company_id")
    private String jobAssignTruckingCompanyId;
    @SerializedName("job_cash_in_advance")
    private String jobCashInAdvance;
    @SerializedName("job_ending_point")
    private String jobEndingPoint;
    @SerializedName("job_load_description")
    private String jobLoadDescription;
    @SerializedName("job_load_number")
    private String jobLoadNumber;
    @SerializedName("job_start_date")
    private String jobStartDate;
    @SerializedName("job_starting_point")
    private String jobStartingPoint;
    @SerializedName("job_truck_driver_name")
    private String jobTruckDriverName;
    @SerializedName("job_truck_driver_phone_number")
    private String jobTruckDriverPhoneNumber;
    @SerializedName("job_truck_height")
    private String jobTruckHeight;
    @SerializedName("job_truck_load_type_id")
    private String jobTruckLoadTypeId;
    @SerializedName("job_truck_number")
    private String jobTruckNumber;
    @SerializedName("job_truck_width")
    private String jobTruckWidth;

    @SerializedName(("job_truck_weight"))
    private String jobTruckWeight;

    public String getJobTruckWeight() {
        return jobTruckWeight;
    }

    public void setJobTruckWeight(String jobTruckWeight) {
        this.jobTruckWeight = jobTruckWeight;
    }

    public String getJobTruckLenght() {
        return jobTruckLenght;
    }

    public void setJobTruckLenght(String jobTruckLenght) {
        this.jobTruckLenght = jobTruckLenght;
    }

    @SerializedName("job_truck_length")
    private String jobTruckLenght;

    public String getJobDotNumber() {
        return jobDotNumber;
    }

    public void setJobDotNumber(String jobDotNumber) {
        this.jobDotNumber = jobDotNumber;
    }

    public String getJobAssignTruckingCompanyId() {
        return jobAssignTruckingCompanyId;
    }

    public void setJobAssignTruckingCompanyId(String jobAssignTruckingCompanyId) {
        this.jobAssignTruckingCompanyId = jobAssignTruckingCompanyId;
    }

    public String getJobCashInAdvance() {
        return jobCashInAdvance;
    }

    public void setJobCashInAdvance(String jobCashInAdvance) {
        this.jobCashInAdvance = jobCashInAdvance;
    }

    public String getJobEndingPoint() {
        return jobEndingPoint;
    }

    public void setJobEndingPoint(String jobEndingPoint) {
        this.jobEndingPoint = jobEndingPoint;
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

    public String getJobStartDate() {
        return jobStartDate;
    }

    public void setJobStartDate(String jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public String getJobStartingPoint() {
        return jobStartingPoint;
    }

    public void setJobStartingPoint(String jobStartingPoint) {
        this.jobStartingPoint = jobStartingPoint;
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

    public String getJobTruckLoadTypeId() {
        return jobTruckLoadTypeId;
    }

    public void setJobTruckLoadTypeId(String jobTruckLoadTypeId) {
        this.jobTruckLoadTypeId = jobTruckLoadTypeId;
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

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
