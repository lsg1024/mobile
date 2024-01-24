package khan.mobile.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.Date;


public class JwtUtil {

    @Value("${SECRET_KEY}")
    private static String secretKey;

    // secretKey 인코딩
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // jwt 토큰 생성
    public static String createJwt(Long user_id, String role) {
        Claims claims = Jwts.claims();
        claims.put("user_id", user_id);
        claims.put("role", role);
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .claim("role", role)
                .setIssuedAt(new Date(now)) // 현재 시간
                .setExpiration(new Date(now + 3600000)) // 만료 시간
                .signWith(SignatureAlgorithm.ES512, secretKey)
                .compact();
    }
    public static Claims validateTokenAndGetClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}
