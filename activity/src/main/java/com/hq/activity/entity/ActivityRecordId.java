package com.hq.activity.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActivityRecordId implements Serializable {

    private Employee employee;
    private Activity activity;
    private Timestamp date;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}