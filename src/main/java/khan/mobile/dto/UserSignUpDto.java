package khan.mobile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import khan.mobile.entity.Role;
import khan.mobile.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSignUpDto {

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "사용할 이름을 입력해주세요")
    @Size(min = 2, message = "이름이 너무 짧습니다")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    private String checkPassword;

    private Role role;

    @Builder
    public Users toEntity() {
        return Users.builder()
                .email(email)
                .name(nickname)
                .password(password)
                .role(Role.USER)
                .build();
    }
}
