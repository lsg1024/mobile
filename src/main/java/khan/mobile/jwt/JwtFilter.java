package khan.mobile.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.oauth2.CustomOAuth2User;
import khan.mobile.dto.UserDto;
import khan.mobile.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    // 인증 전 거름망
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = null;
        Cookie[] cookies = request.getCookies();
        log.info("cookie data = {}", (Object) request.getCookies());
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("Authorization")) {

                    authorization = cookie.getValue();
                }
            }
        }
        else {
            log.warn("cookie data null");
        }

        log.info("authorization = {}", authorization);

        // 토큰 여부 확인
        if (authorization == null) {
            log.error("authorization 잘못되었습니다");
            filterChain.doFilter(request, response);
            return;
        }

        log.info(request.getRequestURI());

        // 토큰 꺼내기
        String token = authorization;

        // 토큰 만료 여부 확인
        if (jwtUtil.isExpired(token)) {
            log.error("authentication 만료");
            filterChain.doFilter(request, response);
            return;
        }

        // user_id role 값 꺼내기
        String username = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);
        log.info("username = {} role = {}", username, role);

        UserDto.OAuth2UserDto oAuth2UserDto = new UserDto.OAuth2UserDto();
        oAuth2UserDto.setName(username);
        oAuth2UserDto.setRole(Role.valueOf(role));

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2UserDto);

        // 스프링 시큐리티 인증 auth 토큰 생성
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

}
