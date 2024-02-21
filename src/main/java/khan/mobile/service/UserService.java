package khan.mobile.service;

import khan.mobile.dto.UserDto;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.exception.AppException;
import khan.mobile.exception.ErrorCode;
import khan.mobile.jwt.JwtUtil;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void createUser(UserDto.signUp userSignUpDto) {

        String email_Lower = userSignUpDto.getEmail().toLowerCase();

        userRepository.findByEmail(email_Lower).ifPresent(user -> {
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, email_Lower + "는 이미 존재하는 이메일 입니다");
        });

        if (!userSignUpDto.getPassword().equals(userSignUpDto.getPassword_confirm())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRMATION_FAILED, "동일한 비밀번호로 작성해주세요");
        }

        Users users = Users.builder()
                .userEmail(email_Lower)
                .name(userSignUpDto.getName())
                .userPassword(encoder.encode(userSignUpDto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(users);

    }

//    @Transactional
//    public String login(UserDto.Login userLoginDto) {

//        String email_Lower = userLoginDto.getEmail().toLowerCase();
//
//        Users selectedUser = userRepository.findByEmail(email_Lower)
//                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, email_Lower + "이 없습니다."));
//
//
//        // 비밀번호 디코딩 후 틀림 여부 확인
//        if (!encoder.matches(userLoginDto.getPassword(), selectedUser.getPassword())) {
//            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호를 잘못 입력 했습니다");
//        }
//
//        return JwtUtil.createJwt(String.valueOf(selectedUser.getUserId()), String.valueOf(selectedUser.getRole()), secretKey, expireTimeMs);
//    }

    public Page<UserDto.UserProfile> getUserList(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDto.UserProfile::userProfile);
    }

}
