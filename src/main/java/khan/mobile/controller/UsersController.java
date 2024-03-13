package khan.mobile.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import khan.mobile.dto.*;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.oauth2.CustomOAuth2User;
import khan.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<CommonResponse> oauthLoginInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        String username = customOAuth2User.getName();

        log.info("userInfo Controller");

        return ResponseEntity.ok().body(new CommonResponse(username));
    }
}
