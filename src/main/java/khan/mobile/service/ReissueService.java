package khan.mobile.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khan.mobile.entity.RefreshEntity;
import khan.mobile.jwt.JwtUtil;
import khan.mobile.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReissueService {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;

        log.info("ReissueService 진입");

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
            return new ResponseEntity<>("Refresh 토큰 만료", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            return new ResponseEntity<>("Cookie : 유효하지 않은 Refresh 토큰", HttpStatus.BAD_REQUEST);
        }

        // DB 저장 확인
        Boolean isExist = refreshRepository.existsByRefreshToken(refresh);
        if (!isExist) {
            return new ResponseEntity<>("DB : 유효하지 않은 Refresh 토큰", HttpStatus.BAD_REQUEST);
        }

        String id = jwtUtil.getUserId(refresh);
        String name = jwtUtil.getName(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", id, name, role, 60 * 1000 * 2L);
        String newRefresh = jwtUtil.createJwt("refresh", id, name, role, 60 * 1000 * 60 * 24L);

        // DB refreshToken 갱신
        refreshRepository.deleteByRefreshToken(newRefresh);
        addRefreshEntity(name, newRefresh, 60 * 1000 * 60 * 24L);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        log.info("ReissueService : new accessToken 발급 완료 = {}", newAccess);
        log.info("ReissueService : new refreshToken 발급 완료 = {}", newRefresh);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    private void addRefreshEntity(String name, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                .name(name)
                .refreshToken(refresh)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}
