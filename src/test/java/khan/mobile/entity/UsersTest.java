package khan.mobile.entity;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UsersTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Test
    void createUser() {
        Users newUser = Users.builder()
                .password("테스트를 위한 비밀번호")
                .role(Role.USER)
                .build();

        userRepository.save(newUser);

        Users findUser = userRepository.findById(newUser.getUser_id()).orElse(null);

        assertNotNull(findUser);
        assertNotNull(findUser.getUser_id());
        assertEquals(newUser.getUser_id(), findUser.getUser_id());

    }
}