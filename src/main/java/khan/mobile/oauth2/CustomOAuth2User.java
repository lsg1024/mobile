package khan.mobile.oauth2;

import khan.mobile.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private UserDto.OAuth2UserDto oAuth2UserDto;
    // test code 실패시 삭제
    private Map<String, Object> attributes;

    public CustomOAuth2User(UserDto.OAuth2UserDto oAuth2UserDto) {
        this.oAuth2UserDto = oAuth2UserDto;
    }

    // test code 실패시 삭제
    public CustomOAuth2User(UserDto.OAuth2UserDto oAuth2UserDto, Map<String, Object> attributes) {
        this.oAuth2UserDto = oAuth2UserDto;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return String.valueOf(oAuth2UserDto.getRole());
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return oAuth2UserDto.getName();
    }

    public String getUsername() {
        return oAuth2UserDto.getUsername();
    }

}
