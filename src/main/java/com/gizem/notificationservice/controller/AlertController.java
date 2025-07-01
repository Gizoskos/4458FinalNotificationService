package com.gizem.notificationservice.controller;

import com.gizem.notificationservice.entity.JobAlert;
import com.gizem.notificationservice.repository.JobAlertRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alerts")
public class AlertController {

    private final JobAlertRepository repository;

    public AlertController(JobAlertRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> createAlert(@RequestBody JobAlert alert) {
        repository.save(alert);
        return ResponseEntity.ok("Alert saved");
    }
}
