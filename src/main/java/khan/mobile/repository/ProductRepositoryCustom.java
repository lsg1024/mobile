package khan.mobile.repository;

import khan.mobile.dto.ProductDto;
import khan.mobile.dto.ProductOrderItemDetailDto;
import khan.mobile.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductOrderItemDetailDto> findOrderItemDetail(List<Long> user_id);

    Page<ProductDto.ProductDataSet> findProductPageable(ProductDto.ProductCondition condition, Pageable pageable);

    ProductDto findProductDetail(Long productId);
}
