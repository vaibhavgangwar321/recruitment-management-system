package Controller;


import DTO.JobRequest;
import Enitity.Job;
import Service.JobService;
import Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class JobController
{

    private final JobService jobService;
    private final UserService userService;

    @PostMapping("/admin/job")
    public ResponseEntity<?> createJob(@RequestParam String adminEmail, @RequestBody JobRequest request)
    {
        User admin = userService.findByEmail(adminEmail);
        if (admin == null || admin.getUserType() != User.UserType.ADMIN)
        {
            return ResponseEntity.badRequest().body("Invalid admin");
        }
        Job job = jobService.createJob(request, admin);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs()
    {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/jobs/apply")
    public ResponseEntity<?> applyJob(@RequestParam String email, @RequestParam Long jobId)
    {
        User applicant = userService.findByEmail(email);

        if (applicant == null || applicant.getUserType() != User.UserType.APPLICANT)
        {
            return ResponseEntity.badRequest().body("Invalid applicant");
        }
        Job job = jobService.getJob(jobId);

        if (job == null) return ResponseEntity.badRequest().body("Job not found");

        jobService.incrementApplications(job);

        return ResponseEntity.ok("Applied successfully");
    }
}