package khan.mobile.oauth2;

import khan.mobile.dto.UserDto;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import khan.mobile.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        Users findUsername = userRepository.findByUsername(username);

        if (findUsername == null) {

            Users createUser = Users.builder()
                    .username(username)
                    .userEmail(oAuth2Response.getEmail())
                    .name(oAuth2Response.getName())
                    .role(Role.USER)
                    .build();

            userRepository.save(createUser);

            UserDto.OAuth2UserDto oAuth2UserDto = new UserDto.OAuth2UserDto();
            oAuth2UserDto.setUsername(username);
            oAuth2UserDto.setName(oAuth2Response.getName());
            oAuth2UserDto.setEmail(oAuth2Response.getEmail());
            oAuth2UserDto.setRole(Role.USER);

            return new CustomOAuth2User(oAuth2UserDto);
        }
        else {
            findUsername.updateOAuth2(oAuth2Response.getName(), oAuth2Response.getEmail());

            userRepository.save(findUsername);

            UserDto.OAuth2UserDto oAuth2UserDto = new UserDto.OAuth2UserDto();
            oAuth2UserDto.setUsername(username);
            oAuth2UserDto.setName(oAuth2Response.getName());
            oAuth2UserDto.setRole(findUsername.getRole());

            return new CustomOAuth2User(oAuth2UserDto);
        }

    }
}
