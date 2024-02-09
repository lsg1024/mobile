package khan.mobile.repository;

import khan.mobile.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("select po from ProductOrder po where po.user.userId = :user_id")
    List<ProductOrder> findByUserId(@Param("user_id") Long user_id);
}
