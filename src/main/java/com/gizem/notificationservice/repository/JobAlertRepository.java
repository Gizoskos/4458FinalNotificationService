package com.gizem.notificationservice.repository;

import com.gizem.notificationservice.entity.JobAlert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobAlertRepository extends MongoRepository<JobAlert, String> {
    List<JobAlert> findByTitleIgnoreCaseOrCityIgnoreCaseOrDepartmentIgnoreCase(String title, String city, String department);
}
