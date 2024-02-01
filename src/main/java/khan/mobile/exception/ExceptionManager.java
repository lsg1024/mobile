package khan.mobile.exception;

import khan.mobile.dto.ErrorResponse;
import khan.mobile.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponse("실패 ", errors));
    }


}
