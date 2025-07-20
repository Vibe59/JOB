package com.jobconnect.repository;

import com.jobconnect.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findByPostedBy(String postedBy);
}
