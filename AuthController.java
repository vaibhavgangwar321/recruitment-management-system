package Controller;


import DTO.LoginRequest;
import DTO.SignupRequest;
import Enitity.User;
import Security.JWTUtil;
import Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request)
    {
        User user = userService.signup(request);
        return ResponseEntity.ok("User registered successfully with email: " + user.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        User user = userService.findByEmail(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
}
