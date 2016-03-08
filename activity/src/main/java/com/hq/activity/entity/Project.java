package com.hq.activity.entity;


import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "PROJECT")
public class Project extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date startDate;

    @Basic(fetch= FetchType.LAZY)
    @Column(nullable = true)
    private Date endDate;

    @OneToMany(mappedBy = "project")
    private Set<Activity> activities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}
