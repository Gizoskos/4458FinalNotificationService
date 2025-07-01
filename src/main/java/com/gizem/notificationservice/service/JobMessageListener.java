package com.gizem.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gizem.notificationservice.entity.JobAlert;
import com.gizem.notificationservice.repository.JobAlertRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobMessageListener {

    private final JobAlertRepository repository;

    public JobMessageListener(JobAlertRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiveMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> job = mapper.readValue(message, Map.class);

            String title = job.get("title");
            String city = job.get("city");
            String department = job.get("department");

            List<JobAlert> matchingAlerts = repository
                    .findByTitleIgnoreCaseOrCityIgnoreCaseOrDepartmentIgnoreCase(title, city, department);

            for (JobAlert alert : matchingAlerts) {
                System.out.println("Notifying user " + alert.getUserId() +
                        " about new job: " + title + " in " + city + " (" + department + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
