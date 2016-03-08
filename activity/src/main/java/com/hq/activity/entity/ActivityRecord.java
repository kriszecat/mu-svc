package com.hq.activity.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
//@IdClass(ActivityRecordId.class)
@Table(name = "ACTIVITY_RECORD")
public class ActivityRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sequence;

    //@Id
    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    private Employee employee;

    //@Id
    @ManyToOne
    @JoinColumn(name = "ACTIVITY_ID")
    private Activity activity;

    @Column(updatable = false)
    private Timestamp date;

    @Column
    private Double duration;

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

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

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }
}
