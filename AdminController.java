package Controller;

import Enitity.Job;
import Enitity.Profile;
import Enitity.User;
import Repository.JobRepository;
import Repository.ProfileRepository;
import Repository.UserRepository;
import Service.JobService;
import Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final JobService jobService;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final JobRepository jobRepository;

    private boolean isAdmin(User user) {
        return user != null && user.getUserType() == User.UserType.ADMIN;
    }

    @GetMapping("/applicants")
    public ResponseEntity<Object> getAllApplicants(@RequestParam String adminEmail) {
        User admin = userService.findByEmail(adminEmail);
        if (!isAdmin(admin)) {
            return ResponseEntity.badRequest().body("Invalid admin");
        }

        List<User> applicants = userRepository.findAll()
                .stream()
                .filter(u -> u.getUserType() == User.UserType.APPLICANT)
                .collect(Collectors.toList());

        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<Object> getApplicant(@RequestParam String adminEmail, @PathVariable Long applicantId) {
        User admin = userService.findByEmail(adminEmail);
        if (!isAdmin(admin)) {
            return ResponseEntity.badRequest().body("Invalid admin");
        }

        Profile profile = (Profile) profileRepository.findByApplicantId(applicantId).orElse(null);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<Object> getJobDetails(@RequestParam String adminEmail, @PathVariable Long jobId) {
        User admin = userService.findByEmail(adminEmail);
        if (!isAdmin(admin)) {
            return ResponseEntity.badRequest().body("Invalid admin");
        }

        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(job);
    }
}