package khan.mobile.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import khan.mobile.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new CommonResponse(e.getMessage()));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<CommonResponse> appExceptionHandler(AppException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new CommonResponse("실패 ", errors));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName = ex.getPath().get(0).getFieldName();
        errors.put(fieldName, "유효하지 않은 값입니다: " + ex.getValue());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
