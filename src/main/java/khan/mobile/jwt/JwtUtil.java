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
    public static String generateToken(Long user_id, String role) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(String.valueOf(user_id))
                .claim("role", role)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600000))
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
