package khan.mobile.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import khan.mobile.dto.*;
import khan.mobile.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static khan.mobile.entity.QProductOrderItem.productOrderItem;
import static khan.mobile.entity.QProducts.products;
import static khan.mobile.entity.QUsers.users;
import static khan.mobile.entity.QFactories.factories;
import static khan.mobile.entity.QProductImage.productImage;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ProductOrderItemDetailDto> findOrderItemDetail(List<Long> order_id) {
        QProductOrderItem poi = productOrderItem;
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


        Map<Long, Long> productFirstImageMap = queryFactory
                .select(productImage.products.productId, productImage.imageId.min())
                .from(productImage)
                .groupBy(productImage.products.productId)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(productImage.products.productId), // 상품 ID를 키로 사용
                        tuple -> tuple.get(productImage.imageId.min()) // 첫 번째 이미지 ID를 값으로 사용, null일 경우에는 null을 맵에 넣음
                ));

        log.info("productFirstImageMap = {}", productFirstImageMap);

        Map<Long, String> productFirstImagePathMap = productFirstImageMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> queryFactory
                                .select(productImage.imagePath)
                                .from(productImage)
                                .where(productImage.imageId.eq(entry.getValue()))
                                .fetchOne()
                ));

        log.info("productFirstImagePathMap = {}", productFirstImagePathMap);

        List<ProductDto.ProductDataSet> content = queryFactory
                .select(new QProductDto_ProductDataSet(
                        products.productId,
                        products.productName,
                        products.productColor,
                        products.productSize,
                        products.productWeight,
                        products.productOther,
                        users.userId,
                        factories.factoryId
                ))
                .from(products)
                .leftJoin(products.user, users)
                .leftJoin(products.factory, factories)
                .where(
                        productNameEq(condition.getProductName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        content.forEach(product -> {
            String imagePath = productFirstImagePathMap.get(product.getId());
            product.setImagePath(imagePath);
        });

        JPAQuery<Long> countQuery = queryFactory
                .select(products.count())
                .from(products)
                .leftJoin(products.user, users)
                .leftJoin(products.factory, factories)
                .where(
                        productNameEq(condition.getProductName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public ProductDto.Detail findProductDetail(Long productId) {

        List<ImagesDto> images = queryFactory
                .select(new QImagesDto(
                        productImage.imageId,
                        productImage.imageName,
                        productImage.imagePath))
                .from(productImage)
                .leftJoin(productImage.products, products)
                .where(products.productId.eq(productId))
                .fetch();

        Tuple result = queryFactory
                .select(products, factories.factoryName)
                .from(products)
                .leftJoin(products.factory, factories)
                .where(products.productId.eq(productId))
                .fetchOne();

        Products productEntity = result.get(products);
        String factoryName = result.get(factories.factoryName);

        return ProductDto.Detail.builder()
                .id(productEntity.getProductId())
                .name(productEntity.getProductName())
                .color(productEntity.getProductColor())
                .size(productEntity.getProductSize())
                .weight(productEntity.getProductWeight())
                .other(productEntity.getProductOther())
                .images(images)
                .userId(productEntity.getUser().getUserId())
                .factoryId(productEntity.getFactory().getFactoryId())
                .factoryName(factoryName)
                .build();
    }


    private BooleanExpression productNameEq(String productName) {
        return !hasText(productName) ? null : products.productName.like("%" + productName + "%");
    }



}
