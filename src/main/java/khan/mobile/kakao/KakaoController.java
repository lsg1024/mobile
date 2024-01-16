package khan.mobile.kakao;

import khan.mobile.kakao.response.KakaoTokenResponse;
import khan.mobile.kakao.response.KakaoUserInfoResponse;
import khan.mobile.kakao.util.KakaoTokenJsonData;
import khan.mobile.kakao.util.KakaoUserInfo;
import khan.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;

    private final UserService userService;

    @GetMapping("/index")
    public String index() {
        return "loginForm";
    }

    @GetMapping("/oauth")
    @ResponseBody
    public Long kakaoOauth(@RequestParam("code") String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
        log.info("회원 정보 입니다.{}",userInfo);

        return userService.createUser(userInfo.getKakao_account().getEmail());
    }
}
