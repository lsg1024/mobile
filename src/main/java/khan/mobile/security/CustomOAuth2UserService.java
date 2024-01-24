package khan.mobile.security;

import jakarta.servlet.http.HttpSession;
import khan.mobile.entity.Users;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

//@Service
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    private final UserRepository userRepository;
//    private final HttpSession httpSession;
//
//    public CustomOAuth2UserService(UserRepository userRepository, HttpSession httpSession) {
//        this.userRepository = userRepository;
//        this.httpSession = httpSession;
//    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        // Extract only email from OAuth2User attributes for Kakao
//        String email = null;
//        if ("kakao".equals(registrationId)) {
//            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
//            if (kakaoAccount != null) {
//                email = (String) kakaoAccount.get("email");
//            }
//        }
//
//        String name = "aa";
//        String password = "12";
//
//        Users user = saveOrUpdate(email, name, password);
//        httpSession.setAttribute("user", new SessionUser(user));
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
//                Collections.singletonMap(userNameAttributeName, email),
//                userNameAttributeName
//        );
//    }
//
//    private Users saveOrUpdate(String email, String name, String password) {
//        Users user = userRepository.findByEmail(email)
//                .map(entity -> entity.update(name, password))
//                .orElse(new Users(email, name, password));
//
//        return userRepository.save(user);
//    }
//
//}
