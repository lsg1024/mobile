package khan.mobile.controller;

import khan.mobile.entity.Users;
import khan.mobile.jwt.JwtUtil;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Users user) {
        Optional<Users> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            String token = JwtUtil.createJwt(user.getUser_id(), String.valueOf(user.getRole()));
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}
