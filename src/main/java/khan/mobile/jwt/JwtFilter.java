package khan.mobile.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.jwt.JwtUtil;
import khan.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    @Value("${SECRET_KEY}")
    private final String secretKey;

    // 인증 전 거름망
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = extractToken(request);

        log.info("authorization = {}", authorization);

        log.info(request.getRequestURI());

        if (isSkippAblePath(request.getRequestURI())) {
            log.info("검증이 필요없는 페이지 입니다 = {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 여부 확인
        if (authorization == null) {
            log.error("authentication 잘못되었습니다");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 꺼내기
        String token = authorization;

        // 토큰 만료 여부 확인
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("authentication 만료");
            filterChain.doFilter(request, response);
            return;
        }

        // user_id role 값 꺼내기
        String user_id = JwtUtil.getUserId(token, secretKey);
        String role = JwtUtil.getRole(token, secretKey);
        log.info("user id = {} role = {}", user_id, role);

        // 스프링 시큐리티 인증 auth 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user_id, null, List.of(new SimpleGrantedAuthority(role)));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }

    private boolean isSkippAblePath(String requestURI) {
        // 로그인, 회원가입, 정적 리소스 경로를 확인
        return requestURI.startsWith("/user/login")
                || requestURI.startsWith("/user/signup")
                || requestURI.startsWith("/user/find_email")
                || requestURI.startsWith("/user/find_password")
                || requestURI.startsWith("/css/")
                || requestURI.startsWith("/js/")
                || requestURI.startsWith("/images/");
    }

    private String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
