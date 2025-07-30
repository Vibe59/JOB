package com.jobconnect;

import com.jobconnect.model.Job;
import com.jobconnect.repository.JobRepository;
import com.jobconnect.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobServiceTest {

    private JobRepository jobRepository;
    private JobService jobService;

    @BeforeEach
    public void setUp() {
        jobRepository = Mockito.mock(JobRepository.class);
        jobService = new JobService(jobRepository);
    }

    @Test
    public void testPostJob() {
        Job job = new Job();
        job.setTitle("Backend Developer");

        when(jobRepository.save(job)).thenReturn(job);

        Job result = jobService.postJob(job);

        assertNotNull(result);
        assertEquals("Backend Developer", result.getTitle());
    }

    @Test
    public void testGetAllJobs() {
        List<Job> jobs = Arrays.asList(new Job(), new Job());

        when(jobRepository.findAll()).thenReturn(jobs);

        List<Job> result = jobService.getAllJobs();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetJobsByPoster() {
        String email = "employer@example.com";
        List<Job> jobs = Arrays.asList(new Job());

        when(jobRepository.findByPostedBy(email)).thenReturn(jobs);

        List<Job> result = jobService.getJobsByPoster(email);

        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteJob() {
        String jobId = "abc123";
        doNothing().when(jobRepository).deleteById(jobId);

        jobService.deleteJob(jobId);

        verify(jobRepository, times(1)).deleteById(jobId);
    }
}
