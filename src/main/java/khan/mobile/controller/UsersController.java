package khan.mobile.controller;

import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.UserJoinRequest;
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
    public ResponseEntity<ResponseDto> signup(@RequestBody UserJoinRequest userSignUpDto) {
        userService.createUser(userSignUpDto.getEmail(), userSignUpDto.getName(), userSignUpDto.getPassword());
        return ResponseEntity.ok(new ResponseDto("회원가입 완료"));
    }


}
