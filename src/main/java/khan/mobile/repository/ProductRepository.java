package khan.mobile.repository;

import khan.mobile.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Products, Long>, ProductRepositoryCustom ,QuerydslPredicateExecutor<Products> {

}
