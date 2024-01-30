package khan.mobile.kakao;

import khan.mobile.kakao.response.KakaoTokenResponse;
import khan.mobile.kakao.response.KakaoUserInfoResponse;
import khan.mobile.kakao.util.KakaoTokenJsonData;
import khan.mobile.kakao.util.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;

    @GetMapping("/oauth/kakao")
    public String kakaoOauth(@RequestParam("code") String code, Model model) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());

        log.info("회원 id 정보 입니다.{}",userInfo.getId());
        log.info("회원 정보 입니다.{}",userInfo.getKakao_account().getEmail());
        log.info("회원 이름 입니다 {}", userInfo.getProperties().getNickname());

        model.addAttribute("email", userInfo.getKakao_account().getEmail());
        model.addAttribute("nickname", userInfo.getProperties().getNickname());

        return "loginPages/signupForm";
    }
}
