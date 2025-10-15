package Controller;


import Enitity.User;
import Service.ResumeService;
import Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applicant")
public class ApplicantController {

    private final UserService userService;
    private final ResumeService resumeService;

    @PostMapping("/uploadResume")
    public ResponseEntity<?> uploadResume(
            @RequestParam("email") String email,
            @RequestParam("file") MultipartFile file) throws Exception {

        User user = userService.findByEmail(email);


        if (user == null || !"APPLICANT".equalsIgnoreCase(String.valueOf(user.getUserType()))) {
            return ResponseEntity.badRequest().body("Invalid applicant");
        }


        resumeService.uploadResume(user, file);

        return ResponseEntity.ok("Resume uploaded successfully");
    }
}