package khan.mobile.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.dto.CustomUserDetail;
import khan.mobile.dto.UserDto;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.exception.AppException;
import khan.mobile.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserDto.SignIn loginDto = new UserDto.SignIn();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginDto = objectMapper.readValue(messageBody, UserDto.SignIn.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("LoginFilter loginData = {}, {}", loginDto.getEmail(), loginDto.getPassword());

        String username = loginDto.getEmail();
        String password = loginDto.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {

        log.info("인증 성공 : successfulAuthentication");
        // 유저 정보
        CustomUserDetail customUserDetails = (CustomUserDetail) authentication.getPrincipal();

        String id = customUserDetails.getId();
        String name = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access", id, name, role, 60 * 1000 * 10L);
        String refresh = jwtUtil.createJwt("refresh", id, name, role, 60 * 1000 * 60 * 24L);

        log.info("LoginFilter access = {}", access);
        log.info("LoginFilter refresh = {}", refresh);
        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        log.info("인증 실패 : unsuccessfulAuthentication");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();

        if (failed instanceof BadCredentialsException) {
            log.info("BadCredentialsException : 잘못된 비빌번호 입력");
            jsonObject.put("message", "이메일 또는 비밀번호를 확인해주세요.");
        } else {
            log.info("Authentication failed : 유효하지 않은 이메일");
            jsonObject.put("message", "이메일 또는 비밀번호를 확인해주세요.");
        }

        response.getWriter().print(jsonObject);

    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}
