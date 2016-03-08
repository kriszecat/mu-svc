package com.hq.activity.repository;

import com.hq.activity.entity.Activity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "activities", path = "activities")
public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long> {
}
