package khan.mobile.controller;

import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.UserSingUpDto;
import khan.mobile.dto.UserLoginDto;
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
    public ResponseEntity<ResponseDto> signup(@RequestBody UserSingUpDto userSingUpDto) {
        userService.createUser(userSingUpDto);
        return ResponseEntity.ok(new ResponseDto("회원가입 완료"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);
        return ResponseEntity.ok().body(token);
    }

}
