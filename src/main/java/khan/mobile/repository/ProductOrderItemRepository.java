package khan.mobile.repository;

import khan.mobile.entity.Product_orderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductOrderItemRepository extends JpaRepository<Product_orderItem, Long>, ProductRepositoryCustom {
}



