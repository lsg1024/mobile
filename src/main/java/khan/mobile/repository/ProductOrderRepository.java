package khan.mobile.repository;

import khan.mobile.entity.Product_order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<Product_order, Long> {

    @Query("select po from Product_order po where po.user.user_id = :user_id")
    List<Product_order> findByUserId(@Param("user_id") Long user_id);
}
