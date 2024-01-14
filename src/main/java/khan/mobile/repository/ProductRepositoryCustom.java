package khan.mobile.repository;

import khan.mobile.dto.ProductOrderItemDetailDto;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductOrderItemDetailDto> findOrderItemDetail(List<Long> user_id);
}
