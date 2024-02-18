package khan.mobile.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class CommonResponse {

    private String message;
    private Map<String, String> errors;

    public CommonResponse(String message) {
        this.message = message;
    }

    public CommonResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

}
