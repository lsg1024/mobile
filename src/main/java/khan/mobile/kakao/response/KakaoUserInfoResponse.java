package khan.mobile.kakao.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KakaoUserInfoResponse {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;
}