package com.midwestpilotcars.models;

import com.midwestpilotcars.models.completejob.CompleteJob;

public class StatusAddDayExpense {
    String status;
    String message;
    DayAddExpense data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DayAddExpense getData() {
        return data;
    }

    public void setData(DayAddExpense data) {
        this.data = data;
    }
}
