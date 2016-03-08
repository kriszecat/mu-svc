package com.hq.activity.entity;

import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;

@Projection(name = "inlineActivity", types = {ActivityRecord.class})
public interface ProjectionActivityRecord {

    Timestamp getDate();
    Double getDuration();
    Activity getActivity();
}
