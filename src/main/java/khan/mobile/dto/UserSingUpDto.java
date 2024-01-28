package khan.mobile.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserSingUpDto {
    private String email;
    private String password;
    private String name;
}
