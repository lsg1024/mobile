package khan.mobile.security;

import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

//@Getter
//@Builder
//public class OAuthAttributes {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;
//    private String email;
//
//    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
//        if ("kakao".equals(registrationId)) {
//            return ofKakao("id", attributes);
//        }
//    }
//
//    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//
//        return OAuthAttributes.builder()
//                .email((String) kakaoAccount.get("email"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public Users toEntity() {
//        return Users.builder()
//                .name(name)
//                .email(email)
//                .role(Role.USER)
//                .build();
//    }
//}