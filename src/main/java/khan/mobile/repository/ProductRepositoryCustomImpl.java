package khan.mobile.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import khan.mobile.dto.ProductDto;
import khan.mobile.dto.ProductOrderItemDetailDto;
import khan.mobile.dto.QProductDto_ProductDataSet;
import khan.mobile.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static khan.mobile.entity.QProductImage.*;
import static khan.mobile.entity.QProducts.products;
import static khan.mobile.entity.QUsers.users;
import static khan.mobile.entity.QFactories.factories;
import static org.springframework.util.StringUtils.hasText;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProductOrderItemDetailDto> findOrderItemDetail(List<Long> order_id) {
        QProductOrderItem poi = QProductOrderItem.productOrderItem;
        QProducts p = products;

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

    @Override
    public Page<ProductDto.ProductDataSet> findProductPageable(ProductDto.ProductCondition condition, Pageable pageable) {

        List<ProductDto.ProductDataSet> content = queryFactory
                .select(new QProductDto_ProductDataSet(
                        products.productId,
                        products.productName,
                        products.productColor,
                        products.productSize,
                        products.productWeight,
                        products.productOther,
                        productImage.imageId,
                        productImage.imagePath,
                        users.userId,
                        factories.factoryId
                ))
                .from(products)
                    .leftJoin(products.productImage, productImage)
                    .leftJoin(products.user, users)
                    .leftJoin(products.factory, factories)
                .where(
                        productNameEq(condition.getProductName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(products.count())
                .from(products)
                .leftJoin(products.productImage, productImage)
                .leftJoin(products.user, users)
                .leftJoin(products.factory, factories)
                .where(
                        productNameEq(condition.getProductName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression productNameEq(String productName) {
        return !hasText(productName) ? null : products.productName.eq(productName);
    }

}
