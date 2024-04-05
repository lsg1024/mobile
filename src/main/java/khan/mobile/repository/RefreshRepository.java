package khan.mobile.repository;

import khan.mobile.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    Boolean existsByRefreshToken(String refreshToken);

    @Transactional
    void deleteByRefreshToken(String refreshToken);

}
