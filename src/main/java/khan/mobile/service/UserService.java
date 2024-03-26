package khan.mobile.service;

import khan.mobile.dto.UserDto;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.exception.AppException;
import khan.mobile.exception.ErrorCode;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public void createUser(UserDto.SignUp userSignUpDto) {

        String email = userSignUpDto.getEmail().toLowerCase();

        if (checkForDuplicateUser(email)) {
            throw new AppException(ErrorCode.EMAIL_DUPLICATE_FAILED, email + " 이미 존재하는 이메일 입니다");
        }

        if (!userSignUpDto.getPassword().equals(userSignUpDto.getPassword_confirm())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRMATION_FAILED, "동일한 비밀번호로 작성해주세요");
        }

        Users users = Users.builder()
                .userEmail(email)
                .name(userSignUpDto.getName())
                .userPassword(encoder.encode(userSignUpDto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(users);

    }

    public Page<UserDto.UserProfile> getUserList(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDto.UserProfile::userProfile);
    }

    public boolean checkForDuplicateUser(String email) {
        // 이메일로 사용자 검색
        Users user = userRepository.findByEmail(email);

        // 사용자가 이미 존재한다면 true를 반환 (중복), 그렇지 않다면 false를 반환 (중복 아님)
        return user != null;
    }

}
