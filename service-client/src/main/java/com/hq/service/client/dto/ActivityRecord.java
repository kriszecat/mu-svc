package com.hq.service.client.dto;

import java.sql.Timestamp;

public class ActivityRecord {

    private Timestamp date;
    private Double duration;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }
}
