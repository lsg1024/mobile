package khan.mobile.service;

import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.UserSignUpDto;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.exception.AppException;
import khan.mobile.exception.ErrorCode;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void createUser(String email, String password, String username) {

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, email + "는 이미 존재하는 이메일 입니다");
        });

        Users users = Users.builder()
                .email(email)
                .name(username)
                .password(encoder.encode(password))
                .role(Role.USER)
                .build();

        userRepository.save(users);

    }

    @Transactional
    public String double_checkUserinfo(String email) {
        log.info("인증 이메일 = {}", email);
        Optional<Users> existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            // 새로운 사용자 등록 페이지로 이동
            return "htmlpages/~~";

        } else {
            return "이미 가입된 사용자 입니다";
        }
    }
}
