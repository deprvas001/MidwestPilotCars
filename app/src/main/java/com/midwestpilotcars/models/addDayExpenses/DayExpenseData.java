package com.midwestpilotcars.models.addDayExpenses;

public class DayExpenseData {
    private String status;
    private String  message;
    private DayExperPerDay data;

    public DayExperPerDay getData() {
        return data;
    }

    public void setData(DayExperPerDay data) {
        this.data = data;
    }

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
}
