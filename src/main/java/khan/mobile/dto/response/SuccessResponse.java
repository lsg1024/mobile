package khan.mobile.dto.response;

import khan.mobile.dto.response.CommonResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse {
    private CommonResponse commonResponse;

    public SuccessResponse(String message) {
        this.commonResponse = new CommonResponse(message);
    }

}

