package com.hq.service.client.dto;

import java.util.Collection;

public class Employee {
    private String employeeID;
    private Collection<ActivityRecord> activityRecords;

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Collection<ActivityRecord> getActivityRecords() {
        return activityRecords;
    }

    public void setActivityRecords(Collection<ActivityRecord> activityRecords) {
        this.activityRecords = activityRecords;
    }
}
