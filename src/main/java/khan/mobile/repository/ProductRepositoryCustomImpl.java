package khan.mobile.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import khan.mobile.dto.ProductOrderItemDetailDto;
import khan.mobile.entity.QProductOrderItem;
import khan.mobile.entity.QProducts;

import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProductOrderItemDetailDto> findOrderItemDetail(List<Long> order_id) {
        QProductOrderItem poi = QProductOrderItem.productOrderItem;
        QProducts p = QProducts.products;

        return queryFactory
                .select(Projections.constructor(ProductOrderItemDetailDto.class,
                        poi.productOrderItemId,
                        poi.productOrderItemColor,
                        poi.productOrderItemSize,
                        poi.productOrderItemOther,
                        poi.productOrderItemQuantity,
                        p.productWeight,
                        p.productName,
                        p.productImage))
                .from(poi)
                .leftJoin(poi.products, p)
                .where(poi.productOrder.productOrderId.in(order_id))
                .fetch();

    }
}
