package khan.mobile.controller;

import jakarta.validation.Valid;
import khan.mobile.dto.*;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService userService;

    @GetMapping("/users")
    public Page<UserDto.UserProfile> getUserList(Pageable pageable) {

        return userService.getUserList(pageable);

    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signUp(@Valid @RequestBody UserDto.SignUp signUpDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(new CommonResponse("회원가입 실패", errors));
        }

        userService.createUser(signUpDto);

        return ResponseEntity.ok(new CommonResponse("회원가입 완료"));
    }

    @GetMapping("/userInfo")
    public ResponseEntity<CommonResponse> oauthLoginInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        String username = principalDetails.getName();

        log.info("userInfo Controller");

        return ResponseEntity.ok().body(new CommonResponse(username));
    }
}
