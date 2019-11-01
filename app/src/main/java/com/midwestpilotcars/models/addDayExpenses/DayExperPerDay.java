package com.midwestpilotcars.models.addDayExpenses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class DayExperPerDay {
    private String job_id;
    @SerializedName("job_pilot_vehile_final_odometer_reading")
    private String odometer_reading;
    @SerializedName("job_pilot_vehile_final_odometer_reading_url")
    private String odometer_url;
    @SerializedName("job_pilot_driver_end_location")
    private String end_location;
    @SerializedName("job_pilot_driver_end_location_latitude")
    private String end_location_latitude;
    @SerializedName("job_pilot_driver_end_location_longitude")
    private String end_location_longitude;
    @SerializedName("job_date")
    private String job_date;
    @SerializedName("job_starting_point")
    private String job_starting_point;
    @SerializedName("job_ending_point")
    private String job_ending_point;
    @SerializedName("job_pilot_driver_start_location")
    private String pilot_start_location;
    @SerializedName("job_pilot_driver_start_location_latitude")
    private String pilot_start_latitude;
    @SerializedName("job_pilot_driver_start_location_longitude")
    private String pilot_start_longitude;
    @SerializedName("job_start_date_time")
    private String start_date_time;
    @SerializedName("job_end_date_time")
    private String end_date_time;
    @SerializedName("job_end_date_time_comment")
    private String end_date_comment;
    @SerializedName("no_go_day")
    private String no_go_day;
    @SerializedName("toal_miles_travlled")
    private String total_mile_travelled;
    @SerializedName("total_mini_amount")
    private String total_mini_amount;
    @SerializedName("count_mini_amount_taken")
    private String count_mini_amount;
    @SerializedName("total_days_filled")
    private String total_days_filled;
    @SerializedName("count_day_rate_taken")
    private String current_day_rate;
    @SerializedName("count_miles_travelled_taken")
    private String count_miles_travelled;
    @SerializedName("day_data")
    private List<DayData> day_Data;


    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getOdometer_reading() {
        return odometer_reading;
    }

    public void setOdometer_reading(String odometer_reading) {
        this.odometer_reading = odometer_reading;
    }

    public String getOdometer_url() {
        return odometer_url;
    }

    public void setOdometer_url(String odometer_url) {
        this.odometer_url = odometer_url;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public String getEnd_location_latitude() {
        return end_location_latitude;
    }

    public void setEnd_location_latitude(String end_location_latitude) {
        this.end_location_latitude = end_location_latitude;
    }

    public String getEnd_location_longitude() {
        return end_location_longitude;
    }

    public void setEnd_location_longitude(String end_location_longitude) {
        this.end_location_longitude = end_location_longitude;
    }

    public String getJob_date() {
        return job_date;
    }

    public void setJob_date(String job_date) {
        this.job_date = job_date;
    }

    public String getJob_starting_point() {
        return job_starting_point;
    }

    public void setJob_starting_point(String job_starting_point) {
        this.job_starting_point = job_starting_point;
    }

    public String getJob_ending_point() {
        return job_ending_point;
    }

    public void setJob_ending_point(String job_ending_point) {
        this.job_ending_point = job_ending_point;
    }

    public String getPilot_start_location() {
        return pilot_start_location;
    }

    public void setPilot_start_location(String pilot_start_location) {
        this.pilot_start_location = pilot_start_location;
    }

    public String getPilot_start_latitude() {
        return pilot_start_latitude;
    }

    public void setPilot_start_latitude(String pilot_start_latitude) {
        this.pilot_start_latitude = pilot_start_latitude;
    }

    public String getPilot_start_longitude() {
        return pilot_start_longitude;
    }

    public void setPilot_start_longitude(String pilot_start_longitude) {
        this.pilot_start_longitude = pilot_start_longitude;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getEnd_date_time() {
        return end_date_time;
    }

    public void setEnd_date_time(String end_date_time) {
        this.end_date_time = end_date_time;
    }

    public String getEnd_date_comment() {
        return end_date_comment;
    }

    public void setEnd_date_comment(String end_date_comment) {
        this.end_date_comment = end_date_comment;
    }

    public String getNo_go_day() {
        return no_go_day;
    }

    public void setNo_go_day(String no_go_day) {
        this.no_go_day = no_go_day;
    }

    public String getTotal_mile_travelled() {
        return total_mile_travelled;
    }

    public void setTotal_mile_travelled(String total_mile_travelled) {
        this.total_mile_travelled = total_mile_travelled;
    }

    public String getTotal_mini_amount() {
        return total_mini_amount;
    }

    public void setTotal_mini_amount(String total_mini_amount) {
        this.total_mini_amount = total_mini_amount;
    }

    public String getCount_mini_amount() {
        return count_mini_amount;
    }

    public void setCount_mini_amount(String count_mini_amount) {
        this.count_mini_amount = count_mini_amount;
    }

    public String getTotal_days_filled() {
        return total_days_filled;
    }

    public void setTotal_days_filled(String total_days_filled) {
        this.total_days_filled = total_days_filled;
    }

    public String getCurrent_day_rate() {
        return current_day_rate;
    }

    public void setCurrent_day_rate(String current_day_rate) {
        this.current_day_rate = current_day_rate;
    }

    public String getCount_miles_travelled() {
        return count_miles_travelled;
    }

    public void setCount_miles_travelled(String count_miles_travelled) {
        this.count_miles_travelled = count_miles_travelled;
    }

    public List<DayData> getDay_Data() {
        return day_Data;
    }

    public void setDay_Data(List<DayData> day_Data) {
        this.day_Data = day_Data;
    }
}
