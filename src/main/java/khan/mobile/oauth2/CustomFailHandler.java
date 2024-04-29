package khan.mobile.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        // 인증 실패 시 클라이언트 측의 URL로 리다이렉트
        log.info("onAuthenticationFailure : 실패");
        String redirectUrl = "http://localhost:3000/";
        response.sendRedirect(redirectUrl);

    }
}
