package khan.mobile.repository;

import khan.mobile.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByProductNameContaining(String name);
}
