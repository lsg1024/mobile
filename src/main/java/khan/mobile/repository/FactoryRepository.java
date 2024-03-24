package khan.mobile.repository;

import khan.mobile.entity.Factories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactoryRepository extends JpaRepository<Factories, Long> {
    Page<Factories> findByFactoryNameContaining(String name, Pageable pageable);

    Optional<Factories> findByFactoryName(String name);
}
