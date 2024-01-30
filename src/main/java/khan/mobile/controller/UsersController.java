package khan.mobile.controller;

import jakarta.validation.Valid;
import khan.mobile.dto.ErrorResponse;
import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.UserSingUpDto;
import khan.mobile.dto.UserLoginDto;
import khan.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid  @RequestBody UserSingUpDto userSingUpDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(new ErrorResponse("회원가입 실패", errors));
        }

        userService.createUser(userSingUpDto);
        return ResponseEntity.ok(new ResponseDto("회원가입 완료"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);
        return ResponseEntity.ok().body(token);
    }

}
