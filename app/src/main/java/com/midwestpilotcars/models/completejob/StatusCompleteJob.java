package com.midwestpilotcars.models.completejob;

import org.json.JSONObject;

public class StatusCompleteJob {
    String status;
    String message;
    CompleteJob data;

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

    public CompleteJob getData() {
        return data;
    }

    public void setData(CompleteJob data) {
        this.data = data;
    }
}
