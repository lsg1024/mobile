package khan.mobile.repository;

import khan.mobile.entity.Factories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactoryRepository extends JpaRepository<Factories, Long> {
}
