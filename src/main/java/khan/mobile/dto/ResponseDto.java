package khan.mobile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseDto {
    private String response;
    public ResponseDto(String response) {
        this.response = response;
    }
}
