package khan.mobile.repository;

import khan.mobile.entity.Product_orderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderItemRepository extends JpaRepository<Product_orderItem, Long> {

    @Query("SELECT poi FROM Product_orderItem poi WHERE poi.product_order.product_order_id IN :orderIds")
    List<Product_orderItem> findByProductOrderIdIn(@Param("orderIds") List<Long> orderIds);
}



