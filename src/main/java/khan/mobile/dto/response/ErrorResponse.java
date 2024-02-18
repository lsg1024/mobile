package khan.mobile.dto.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
