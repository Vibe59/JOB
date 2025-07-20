package com.jobconnect.service;

import com.jobconnect.model.Job;
import com.jobconnect.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> getJobsByPoster(String email) {
        return jobRepository.findByPostedBy(email);
    }

    public void deleteJob(String jobId) {
        jobRepository.deleteById(jobId);
    }
}
