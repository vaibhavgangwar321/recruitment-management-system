package Service;


import DTO.SignupRequest;
import Enitity.Profile;
import Enitity.User;
import Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignupRequest request)
    {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setProfileHeadline(request.getProfileHeadline());
        user.setUserType(User.UserType.valueOf(request.getUserType())); // assuming String type (e.g. "ADMIN", "APPLICANT")
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        if ("APPLICANT".equalsIgnoreCase(request.getUserType())) {
            Profile profile = new Profile();
            profile.setApplicant(user);
            user.setProfile((org.springframework.context.annotation.Profile) profile);
        }

        return userRepository.save(user);
    }

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email).orElse(null);
    }
}
