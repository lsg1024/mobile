package khan.mobile.oauth2;

import khan.mobile.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final UserDto.OAuth2UserDto oAuth2UserDto;

    public CustomOAuth2User(UserDto.OAuth2UserDto oAuth2UserDto) {
        this.oAuth2UserDto = oAuth2UserDto;
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


    public String getId() {
        return oAuth2UserDto.getId();
    }

}
