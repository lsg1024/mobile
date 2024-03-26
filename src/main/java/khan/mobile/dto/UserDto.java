package khan.mobile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class  UserDto{

    @Getter @Setter
    public static class OAuth2UserDto {
        private String id;
        private String email;
        private String name;
        private String username;
        private Role role;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignUp {
        @NotBlank(message = "이메일을 입력해주세요.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@!%*#?&]).{8,16}$",
                message = "비밀번호는 영문자, 숫자, 특수문자를 포함한 8~16자리여야 합니다.")
        private String password;

        @NotBlank(message = "비밀번호 확인을 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@!%*#?&]).{8,16}$",
                message = "비밀번호는 영문자, 숫자, 특수문자를 포함한 8~16자리여야 합니다.")
        private String password_confirm;

        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SignIn {
        @Email
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class UserProfile {

        private Long userId;
        private String userEmail;
        private String userName;
        private Role role;
        private String lastModifiedDate;

        public static UserProfile userProfile(Users users) {
            return new UserProfile(
                    users.getUserId(),
                    users.getEmail(),
                    users.getName(),
                    users.getRole(),
                    users.getLastModifiedDate());
        }
    }
}
