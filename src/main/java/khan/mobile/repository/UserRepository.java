package khan.mobile.repository;

import khan.mobile.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    Users findByUsername(String username);

    Users findByUsernameOrEmail(String username, String email);
}
