package khan.mobile.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.dto.PrincipalDetails;
import khan.mobile.entity.RefreshEntity;
import khan.mobile.jwt.JwtUtil;
import khan.mobile.repository.RefreshRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomSuccessHandler(JwtUtil jwtUtil, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
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

        // 리프레시 토큰 DB 저장
        log.info("onAuthenticationSuccess addRefreshEntity");
        addRefreshEntity(name, refresh, 60 * 1000 * 60 * 24L);

        log.info("onAuthenticationSuccess refresh = {}", refresh);
        response.addCookie(createCookie("refresh", refresh, true));

        response.sendRedirect("http://localhost:3000/home");
    }

    private void addRefreshEntity(String name, String refreshToken, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                .name(name)
                .refreshToken(refreshToken)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshEntity);

    }

    private Cookie createCookie(String key, String value, boolean httpOnly) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(1000 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);

        return cookie;
    }
}
