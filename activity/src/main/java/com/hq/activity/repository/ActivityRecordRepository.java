package com.hq.activity.repository;

import com.hq.activity.entity.ActivityRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "activityRecords", path = "activityRecords")
public interface ActivityRecordRepository extends PagingAndSortingRepository<ActivityRecord, Long> {

    @Query("SELECT rec from ActivityRecord rec LEFT JOIN rec.employee e WHERE e.employeeID = :eid")
    Page<ActivityRecord> findByEmployeeId(@Param("eid") String lastname, Pageable pageable);
}
