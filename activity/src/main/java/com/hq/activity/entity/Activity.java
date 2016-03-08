package com.hq.activity.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ACTIVITY")
public class Activity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @OneToMany(mappedBy = "activity")
    private Collection<ActivityRecord> activityRecords;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Collection<ActivityRecord> getActivityRecords() {
        return activityRecords;
    }

    public void setActivityRecords(Collection<ActivityRecord> activityRecords) {
        this.activityRecords = activityRecords;
    }
}
