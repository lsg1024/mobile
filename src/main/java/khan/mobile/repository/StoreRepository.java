package khan.mobile.repository;

import khan.mobile.entity.Stores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StoreRepository extends JpaRepository<Stores, Long>, QuerydslPredicateExecutor<Stores> {
    Page<Stores> findStoresByStoreNameContaining(String name, Pageable pageable);
}
