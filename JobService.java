package Service;

import DTO.JobRequest;
import Enitity.Job;
import Repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job createJob(JobRequest request, User admin) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompanyName(request.getCompanyName());
        job.setPostedOn(LocalDateTime.now());
        job.setTotalApplications(0);
        job.setPostedBy(admin);

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs()
    {
        return jobRepository.findAll();
    }

    public Job getJob(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    public void incrementApplications(Job job) {
        job.setTotalApplications(job.getTotalApplications() + 1);
        jobRepository.save(job);
    }
}