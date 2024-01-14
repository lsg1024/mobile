package khan.mobile.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import khan.mobile.dto.ProductOrderItemDetailDto;
import khan.mobile.entity.QProduct_orderItem;
import khan.mobile.entity.QProducts;

import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProductOrderItemDetailDto> findOrderItemDetail(List<Long> order_id) {
        QProduct_orderItem poi = QProduct_orderItem.product_orderItem;
        QProducts p = QProducts.products;

        return queryFactory
                .select(Projections.constructor(ProductOrderItemDetailDto.class,
                        poi.product_orderItem_id,
                        poi.product_orderItem_color,
                        poi.product_orderItem_size,
                        poi.product_orderItem_other,
                        poi.product_orderItem_quantity,
                        p.product_weight,
                        p.product_name,
                        p.product_image))
                .from(poi)
                .leftJoin(poi.products, p)
                .where(poi.product_order.product_order_id.in(order_id))
                .fetch();

    }
}
