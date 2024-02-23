package khan.mobile.oauth2;

import khan.mobile.dto.UserDto;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.exception.EmailAlreadyExistsException;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User Data = {}" ,oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoMemberInfoResponse(oAuth2User.getAttributes());
        }
        else {
            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        String email = oAuth2Response.getEmail();

        Users findUsernameAndEmail = userRepository.findByUsernameOrEmail(username, email);

        if (findUsernameAndEmail.getEmail() != null && findUsernameAndEmail.getUsername() == null) {
            log.info("이미 존재하는 회원");

            return null;
        }

        log.info("findUsernameAndEmail = {}", findUsernameAndEmail);

        if (findUsernameAndEmail.getUsername() == null) {

            Users createUser = Users.builder()
                    .username(username)
                    .userEmail(email)
                    .name(oAuth2Response.getName())
                    .role(Role.USER)
                    .build();

            userRepository.save(createUser);

            UserDto.OAuth2UserDto oAuth2UserDto = new UserDto.OAuth2UserDto();
            oAuth2UserDto.setUsername(username);
            oAuth2UserDto.setName(oAuth2Response.getName());
            oAuth2UserDto.setEmail(email);
            oAuth2UserDto.setRole(Role.USER);

            return new CustomOAuth2User(oAuth2UserDto);

        }
        else if (findUsernameAndEmail.getEmail() != null && findUsernameAndEmail.getUsername() == null) {
            log.info("이미 존재하는 회원");

            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        else {
            findUsernameAndEmail.updateOAuth2(oAuth2Response.getName(), email);

            userRepository.save(findUsernameAndEmail);

            UserDto.OAuth2UserDto oAuth2UserDto = new UserDto.OAuth2UserDto();
            oAuth2UserDto.setUsername(username);
            oAuth2UserDto.setName(oAuth2Response.getName());
            oAuth2UserDto.setRole(findUsernameAndEmail.getRole());

            return new CustomOAuth2User(oAuth2UserDto);
        }

    }
}
