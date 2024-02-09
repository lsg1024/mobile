package khan.mobile.repository;

import khan.mobile.entity.ProductOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductOrderItemRepository extends JpaRepository<ProductOrderItem, Long>, ProductRepositoryCustom {
}



