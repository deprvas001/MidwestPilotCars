
package com.midwestpilotcars.models.getAllJobs;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UPCOMING implements Parcelable {

    @SerializedName("company_logo")
    private String companyLogo;
    @SerializedName("days_filled")
    private String daysFilled;
    @SerializedName("end_trip_status")
    private String endTripStatus;
    @SerializedName("jab_dot_number")
    private String jabDotNumber;
    @SerializedName("job_cash_in_advance")
    private String jobCashInAdvance;
    @SerializedName("job_comments")
    private String jobComments;
    @SerializedName("job_detention_price")
    private String jobDetentionPrice;
    @SerializedName("job_ending_point")
    private String jobEndingPoint;
    @SerializedName("Job_id")
    private String jobId;
    @SerializedName("job_load_description")
    private String jobLoadDescription;
    @SerializedName("job_load_number")
    private String jobLoadNumber;
    @SerializedName("job_no_goes_price")
    private String jobNoGoesPrice;
    @SerializedName("job_pay_per_mile_cost")
    private String jobPayPerMileCost;
    @SerializedName("job_per_day_cost")
    private String jobPerDayCost;
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
    @SerializedName("load_type_name")
    private String loadTypeName;
    @SerializedName("trucking_company_id")
    private String truckingCompanyId;
    @SerializedName("trucking_company_logo")
    private String truckingCompanyLogo;
    @SerializedName("trucking_company_name")
    private String truckingCompanyName;
    @SerializedName("trucking_company_rep_name")
    private String truckingCompanyRepName;
    @SerializedName("job_truck_weight")
    private String job_truck_weight;
    @SerializedName("job_truck_length")
    private String job_truck_length;




    protected UPCOMING(Parcel in) {
        companyLogo = in.readString();
        daysFilled = in.readString();
        endTripStatus = in.readString();
        jabDotNumber = in.readString();
        jobCashInAdvance = in.readString();
        jobComments = in.readString();
        jobDetentionPrice = in.readString();
        jobEndingPoint = in.readString();
        jobId = in.readString();
        jobLoadDescription = in.readString();
        jobLoadNumber = in.readString();
        jobNoGoesPrice = in.readString();
        jobPayPerMileCost = in.readString();
        jobPerDayCost = in.readString();
        jobStartDate = in.readString();
        jobStartingPoint = in.readString();
        jobTruckDriverName = in.readString();
        jobTruckDriverPhoneNumber = in.readString();
        jobTruckHeight = in.readString();
        jobTruckLoadTypeId = in.readString();
        jobTruckNumber = in.readString();
        jobTruckWidth = in.readString();
        loadTypeName = in.readString();
        truckingCompanyId = in.readString();
        truckingCompanyLogo = in.readString();
        truckingCompanyName = in.readString();
        truckingCompanyRepName = in.readString();

        job_truck_weight = in.readString();
        job_truck_length = in.readString();

    }


    public static final Creator<UPCOMING> CREATOR = new Creator<UPCOMING>() {
        @Override
        public UPCOMING createFromParcel(Parcel in) {
            return new UPCOMING(in);
        }

        @Override
        public UPCOMING[] newArray(int size) {
            return new UPCOMING[size];
        }
    };

   /* public String getJob_load_no() {
        return job_load_no;
    }

    public void setJob_load_no(String job_load_no) {
        this.job_load_no = job_load_no;
    }

    public String getJob_load_description() {
        return job_load_description;
    }

    public void setJob_load_description(String job_load_description) {
        this.job_load_description = job_load_description;
    }*/

   /* public String getJob_dot_number() {
        return job_dot_number;
    }

    public void setJob_dot_number(String job_dot_number) {
        this.job_dot_number = job_dot_number;
    }*/

    /*public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }*/

   /* public String getDriver_phone_number() {
        return driver_phone_number;
    }

    public void setDriver_phone_number(String driver_phone_number) {
        this.driver_phone_number = driver_phone_number;
    }*/

   /* public String getTruck_number() {
        return truck_number;
    }

    public void setTruck_number(String truck_number) {
        this.truck_number = truck_number;
    }*/

  /*  public String getJob_truck_height() {
        return job_truck_height;
    }

    public void setJob_truck_height(String job_truck_height) {
        this.job_truck_height = job_truck_height;
    }*/

  /*  public String getJob_truck_width() {
        return job_truck_width;
    }

    public void setJob_truck_width(String job_truck_width) {
        this.job_truck_width = job_truck_width;
    }*/

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

 /*   public String getJob_comments() {
        return job_comments;
    }

    public void setJob_comments(String job_comments) {
        this.job_comments = job_comments;
    }*/

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getDaysFilled() {
        return daysFilled;
    }

    public void setDaysFilled(String daysFilled) {
        this.daysFilled = daysFilled;
    }

    public String getEndTripStatus() {
        return endTripStatus;
    }

    public void setEndTripStatus(String endTripStatus) {
        this.endTripStatus = endTripStatus;
    }

    public String getJabDotNumber() {
        return jabDotNumber;
    }

    public void setJabDotNumber(String jabDotNumber) {
        this.jabDotNumber = jabDotNumber;
    }

    public String getJobCashInAdvance() {
        return jobCashInAdvance;
    }

    public void setJobCashInAdvance(String jobCashInAdvance) {
        this.jobCashInAdvance = jobCashInAdvance;
    }

    public String getJobComments() {
        return jobComments;
    }

    public void setJobComments(String jobComments) {
        this.jobComments = jobComments;
    }

    public String getJobDetentionPrice() {
        return jobDetentionPrice;
    }

    public void setJobDetentionPrice(String jobDetentionPrice) {
        this.jobDetentionPrice = jobDetentionPrice;
    }

    public String getJobEndingPoint() {
        return jobEndingPoint;
    }

    public void setJobEndingPoint(String jobEndingPoint) {
        this.jobEndingPoint = jobEndingPoint;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public String getJobNoGoesPrice() {
        return jobNoGoesPrice;
    }

    public void setJobNoGoesPrice(String jobNoGoesPrice) {
        this.jobNoGoesPrice = jobNoGoesPrice;
    }

    public String getJobPayPerMileCost() {
        return jobPayPerMileCost;
    }

    public void setJobPayPerMileCost(String jobPayPerMileCost) {
        this.jobPayPerMileCost = jobPayPerMileCost;
    }

    public String getJobPerDayCost() {
        return jobPerDayCost;
    }

    public void setJobPerDayCost(String jobPerDayCost) {
        this.jobPerDayCost = jobPerDayCost;
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

    public String getLoadTypeName() {
        return loadTypeName;
    }

    public void setLoadTypeName(String loadTypeName) {
        this.loadTypeName = loadTypeName;
    }

    public String getTruckingCompanyId() {
        return truckingCompanyId;
    }

    public void setTruckingCompanyId(String truckingCompanyId) {
        this.truckingCompanyId = truckingCompanyId;
    }

    public String getTruckingCompanyLogo() {
        return truckingCompanyLogo;
    }

    public void setTruckingCompanyLogo(String truckingCompanyLogo) {
        this.truckingCompanyLogo = truckingCompanyLogo;
    }

    public String getTruckingCompanyName() {
        return truckingCompanyName;
    }

    public void setTruckingCompanyName(String truckingCompanyName) {
        this.truckingCompanyName = truckingCompanyName;
    }

    public String getTruckingCompanyRepName() {
        return truckingCompanyRepName;
    }

    public void setTruckingCompanyRepName(String truckingCompanyRepName) {
        this.truckingCompanyRepName = truckingCompanyRepName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyLogo);
        dest.writeString(daysFilled);
        dest.writeString(endTripStatus);
        dest.writeString(jabDotNumber);
        dest.writeString(jobCashInAdvance);
        dest.writeString(jobComments);
        dest.writeString(jobDetentionPrice);
        dest.writeString(jobEndingPoint);
        dest.writeString(jobId);
        dest.writeString(jobLoadDescription);
        dest.writeString(jobLoadNumber);
        dest.writeString(jobNoGoesPrice);
        dest.writeString(jobPayPerMileCost);
        dest.writeString(jobPerDayCost);
        dest.writeString(jobStartDate);
        dest.writeString(jobStartingPoint);
        dest.writeString(jobTruckDriverName);
        dest.writeString(jobTruckDriverPhoneNumber);
        dest.writeString(jobTruckHeight);
        dest.writeString(jobTruckLoadTypeId);
        dest.writeString(jobTruckNumber);
        dest.writeString(jobTruckWidth);
        dest.writeString(loadTypeName);
        dest.writeString(truckingCompanyId);
        dest.writeString(truckingCompanyLogo);
        dest.writeString(truckingCompanyName);
        dest.writeString(truckingCompanyRepName);

        dest.writeString(job_truck_weight);
        dest.writeString(job_truck_length );

    }
}
