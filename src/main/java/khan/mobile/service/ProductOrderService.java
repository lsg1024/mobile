package khan.mobile.service;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.dto.ProductOrderItemDto;
import khan.mobile.entity.*;
import khan.mobile.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;


    // 상품 주문
    @Transactional
    public void createOrder(Long user_id, Long store_id, ProductOrderDto productOrderDto) {
        //관련 엔티티 조회
        Users user = userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("일치하는 유저 정보가 없습니다"));
        Stores store = storeRepository.findById(store_id).orElseThrow(() -> new IllegalArgumentException("일치하는 상점 정보가 없습니다"));

        // 주문 엔티티 생성
        Product_order order = Product_order.builder()
                .user(user)
                .stores(store)
                .build();

        createOrderItems(productOrderDto, order);

        productOrderRepository.save(order);

    }

    // 상품 수정
//    @Transactional
//    public void updateOrder(Long user_id, Long store, Long product_order_id, ProductOrderDto productOrderDto) {
//
//        Optional<Product_order> findProduct_order = productOrderRepository.findById(product_order_id);
//
//        findProduct_order.
//
//    }

    private void createOrderItems(ProductOrderDto productOrderDto, Product_order order) {
        for (ProductOrderItemDto itemDto : productOrderDto.getOrderItems()) {
            log.info("제품 ID 정보: {}", itemDto.getProduct_id());

            Products product = productRepository.findById(itemDto.getProduct_id()).orElseThrow(() -> new IllegalArgumentException("일치하는 제품 정보가 없습니다"));

            Product_orderItem orderItem = Product_orderItem.builder()
                    .product_orderItem_color(itemDto.getColor())
                    .product_orderItem_size(itemDto.getSize())
                    .product_orderItem_other(itemDto.getOther())
                    .product_orderItem_quantity(itemDto.getQuantity())
                    .products(product)
                    .product_order(order)
                    .build();

            productOrderItemRepository.save(orderItem);
        }
    }
}
