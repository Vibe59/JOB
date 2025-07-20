package com.jobconnect.controller;

import com.jobconnect.model.Job;
import com.jobconnect.model.User;
import com.jobconnect.service.JobService;
import com.jobconnect.service.TwilioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "Job APIs", description = "Endpoints for Employer and Seeker Dashboard")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private TwilioService twilioService;

    @Operation(summary = "Employer dashboard with posted jobs and job form")
    @GetMapping("/employer/dashboard")
    public String employerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("EMPLOYER")) {
            return "redirect:/";
        }
        model.addAttribute("job", new Job());
        model.addAttribute("jobs", jobService.getJobsByPoster(user.getEmail()));
        return "employer-dashboard";
    }

    @Operation(summary = "Post a new job by employer")
    @PostMapping("/employer/postJob")
    public String postJob(@ModelAttribute Job job, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("EMPLOYER")) {
            return "redirect:/";
        }
        job.setPostedBy(user.getEmail());
        jobService.postJob(job);

        // Send dummy SMS
        twilioService.sendSms("+911234567890", "New job posted by " + user.getEmail());

        return "redirect:/employer/dashboard";
    }

    @Operation(summary = "Job seeker dashboard to view all jobs")
    @GetMapping("/seeker/dashboard")
    public String seekerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("JOB_SEEKER")) {
            return "redirect:/";
        }
        model.addAttribute("jobs", jobService.getAllJobs());
        return "seeker-dashboard";
    }

    @Operation(summary = "Delete a job posted by employer")
    @PostMapping("/employer/delete/{id}")
    public String deleteJob(@PathVariable String id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("EMPLOYER")) {
            return "redirect:/";
        }
        jobService.deleteJob(id);
        return "redirect:/employer/dashboard";
    }
}
