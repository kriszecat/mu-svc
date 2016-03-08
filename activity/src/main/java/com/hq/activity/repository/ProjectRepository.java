package com.hq.activity.repository;

import com.hq.activity.entity.Project;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "projects", path = "projects")
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
}
