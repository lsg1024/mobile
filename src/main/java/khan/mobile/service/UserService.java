package khan.mobile.service;

import khan.mobile.dto.UserLoginDto;
import khan.mobile.dto.UserSingUpDto;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.exception.AppException;
import khan.mobile.exception.ErrorCode;
import khan.mobile.jwt.JwtUtil;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;
    private Long expireTimeMs = 1000 * 60 * 60l;

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Transactional
    public void createUser(UserSingUpDto userSingUpDto) {

        userRepository.findByEmail(userSingUpDto.getEmail()).ifPresent(user -> {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, userSingUpDto.getEmail() + "는 이미 존재하는 이메일 입니다");
        });

        Users users = Users.builder()
                .email(userSingUpDto.getEmail())
                .name(userSingUpDto.getName())
                .password(encoder.encode(userSingUpDto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(users);

    }

    @Transactional
    public String login(UserLoginDto userLoginDto) {
        Users selectedUser = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userLoginDto.getEmail() + "이 없습니다."));


        // 비밀번호 디코딩 후 틀림 여부 확인
        if (encoder.matches(selectedUser.getPassword(), userLoginDto.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호를 잘못 입력 했습니다");
        }

        return JwtUtil.createJwt(String.valueOf(selectedUser.getUser_id()), String.valueOf(selectedUser.getRole()), secretKey, expireTimeMs);
    }


}
