package com.hq.activity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends AbstractEntity {
    @Column(name = "EID", nullable = false)
    private String employeeID;

    @OneToMany(mappedBy="employee")
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
