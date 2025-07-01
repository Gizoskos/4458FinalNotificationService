package com.gizem.notificationservice.service;

import com.gizem.notificationservice.entity.Job;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobQueueService {

    private final List<Job> newJobs = new ArrayList<>();

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiveJob(Job job) {
        System.out.println(" Yeni ilan alındı: " + job.getTitle());
        newJobs.add(job);
    }

    public List<Job> getNewJobsFromQueue() {
        List<Job> copy = new ArrayList<>(newJobs);
        newJobs.clear(); // Tüketildi say
        return copy;
    }
}
