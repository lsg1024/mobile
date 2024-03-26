package khan.mobile.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.dto.PrincipalDetails;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails customUserDetails = (PrincipalDetails) authentication.getPrincipal();

        String id = customUserDetails.getId();
        String name = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String refresh = jwtUtil.createJwt("refresh", id, name, role, 60 * 1000 * 60 * 24L);

        response.addCookie(createCookie("refresh", refresh, true));

        response.sendRedirect("http://localhost:3000/home");
    }

    private Cookie createCookie(String key, String value, boolean httpOnly) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(1000 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);

        return cookie;
    }
}
