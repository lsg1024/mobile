package khan.mobile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.dto.UserDto;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.exception.AppException;
import khan.mobile.exception.ErrorCode;
import khan.mobile.jwt.JwtUtil;
import khan.mobile.oauth2.CustomOAuth2User;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final JwtUtil jwtUtil;

    @Value("${SECRET_KEY}")
    private String secretKey;

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

    @Transactional
    public String login(UserDto.SignIn userSignInDto) {

        String email_Lower = userSignInDto.getEmail().toLowerCase();

        Users selectedUser = userRepository.findByEmail(email_Lower);


        // 비밀번호 디코딩 후 틀림 여부 확인
        if (!encoder.matches(userSignInDto.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "비밀번호를 잘못 입력 했습니다");
        }

        // JWT 생성
        Long expireTimeMs = 1000 * 60 * 60L;
        String token = jwtUtil.createJwt("access", String.valueOf(selectedUser.getUserId()), selectedUser.getName(), String.valueOf(selectedUser.getRole()), expireTimeMs);

        // 스프링 시큐리티 인증 처리
        UserDto.OAuth2UserDto oAuth2UserDto = new UserDto.OAuth2UserDto();
        oAuth2UserDto.setId(String.valueOf(selectedUser.getUserId()));
        oAuth2UserDto.setName(selectedUser.getName());
        oAuth2UserDto.setRole(selectedUser.getRole());

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2UserDto);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return token;
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
