package khan.mobile.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReissueService {

    private final JwtUtil jwtUtil;

    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("Refresh 토큰 없음", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("Access 토큰 만료", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return new ResponseEntity<>("유효하지 않은 Refresh 토큰", HttpStatus.BAD_REQUEST);
        }

        String id = jwtUtil.getUserId(refresh);
        String name = jwtUtil.getName(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", id, name, role, 60 * 1000 * 10L);
        String newRefresh = jwtUtil.createJwt("refresh", id, name, role, 60 * 1000 * 60 * 24L);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        log.info("ReissueService : new accessToken 발급 완료");
        log.info("ReissueService : new refreshToken 발급 완료");

        return new ResponseEntity<>(HttpStatus.OK);

    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}
