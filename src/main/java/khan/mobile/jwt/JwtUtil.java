package khan.mobile.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;


@Slf4j
public class JwtUtil {

    public static String getUserId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("user_id", String.class);
    }

    public static String getRole(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("role", String.class);
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    // jwt 토큰 생성
    public static String createJwt(String user_id, String role, String key, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("user_id", user_id);
        claims.put("role", role);
        log.info("user_id = {}", user_id);
        log.info("role = {}", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 현재 시간
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }


}
