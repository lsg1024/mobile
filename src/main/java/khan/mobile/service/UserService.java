package khan.mobile.service;

import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(String email) {
        Users user = Users.builder()
                .email(email)
                .role(Role.USER)
                .build();

        userRepository.save(user);
        log.info("새로운 회원 저장 완료");
        return user.getUser_id();
    }

    @Transactional
    public String double_checkUserinfo(String email) {
        log.info("인증 이메일 = {}" ,email);
        Users existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            // 새로운 사용자 등록 페이지로 이동
            return "htmlpages/~~";

        } else {
            return "이미 가입된 사용자 입니다";
        }
    }
}
