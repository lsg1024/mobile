package khan.mobile.service;

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
                .build();

        userRepository.save(user);
        log.info("새로운 회원 저장 완료");
        return user.getUser_id();
    }
}
