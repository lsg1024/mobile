package khan.mobile.service;

import khan.mobile.dto.UserSignUpDto;
import khan.mobile.entity.Users;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createUser(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일 입니다.");
        }

        if (!userSignUpDto.getPassword().equals(userSignUpDto.getCheckPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        Users user = userRepository.save(userSignUpDto.toEntity());
//        user.encodePassword(passwordEncoder);

        return user.getUser_id();
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
