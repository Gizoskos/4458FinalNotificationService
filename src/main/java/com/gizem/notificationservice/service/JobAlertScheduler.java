package com.gizem.notificationservice.service;

import com.gizem.notificationservice.entity.Job;
import com.gizem.notificationservice.entity.JobAlert;
import com.gizem.notificationservice.repository.JobAlertRepository;
import com.gizem.notificationservice.service.JobQueueService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobAlertScheduler {

    private final JobQueueService jobQueueService;
    private final JobAlertRepository jobAlertRepository;

    public JobAlertScheduler(JobQueueService jobQueueService, JobAlertRepository jobAlertRepository) {
        this.jobQueueService = jobQueueService;
        this.jobAlertRepository = jobAlertRepository;
    }

    @Scheduled(fixedRate = 10000) // her 10 saniyede bir kontrol eder
    public void checkJobAlerts() {
        List<Job> newJobs = jobQueueService.getNewJobsFromQueue(); // Kuyruktan al
        List<JobAlert> alerts = jobAlertRepository.findAll();

        for (JobAlert alert : alerts) {
            for (Job job : newJobs) {
                if (job.getTitle() != null && alert.getTitle() != null &&
                        job.getTitle().toLowerCase().contains(alert.getTitle().toLowerCase())) {
                    System.out.println("ALERT: User " + alert.getUserId() +
                            " için yeni iş ilanı bulundu: " + job.getTitle());
                }
            }
        }
    }
}
