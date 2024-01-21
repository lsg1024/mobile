package khan.mobile.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class ResponseDto {
    private String response;
    private Map<String, String> errors;
    public ResponseDto(String response) {
        this.response = response;
    }
    public ResponseDto(Map<String, String> errors) {
        this.errors = errors;}
}
