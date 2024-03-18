package khan.mobile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import khan.mobile.dto.UserDto;
import khan.mobile.exception.AppException;
import khan.mobile.exception.ErrorCode;
import khan.mobile.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO
 * 현재 테스트 파일은 실행만 가능할 뿐 코드 수정이 필요함
 */
@WebMvcTest(UsersController.class)
@EnableJpaAuditing
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(SecurityFilterChain.class)
class UsersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;



    @Test
    @DisplayName("회원가입 성공")
    void createUser() throws Exception {

        String email = "sdf@naver.com";
        String password = "js52845284!";
        String password_confirm = "js52845284!";
        String name = "js";

        mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserDto.SignUp(email, password, password_confirm, name))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    void createUserFail() throws Exception {

        String email = "zks15@naver.com";
        String password = "js52845284!";
        String password_confirm = "js52845284!";
        String name = "js";

        mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserDto.SignUp(email, password, password_confirm, name))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

//    @Test
//    @DisplayName("로그인 성공")
//    @WithAnonymousUser
//    void login_success() throws Exception{
//
//        String email = "sdf@nave.com";
//        String password = "js52845284!";
//
//        UserDto.SignIn userSignInDto = new UserDto.SignIn(email, password);
//
//        when(userService.login(userSignInDto))
//                .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));
//
//        mockMvc.perform(post("/user/login")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(new UserDto.SignIn(email, password))))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
}