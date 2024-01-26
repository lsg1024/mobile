package khan.mobile.controller;

import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.UserJoinRequest;
import khan.mobile.dto.UserLoginRequest;
import khan.mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody UserJoinRequest userJoinRequest) {
        userService.createUser(userJoinRequest.getEmail(), userJoinRequest.getName(), userJoinRequest.getPassword());
        return ResponseEntity.ok(new ResponseDto("회원가입 완료"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginDto) {
        String token = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
        return ResponseEntity.ok().body(token);
    }


}
