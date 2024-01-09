package khan.mobile.service;

import jakarta.persistence.EntityNotFoundException;
import khan.mobile.dto.ProductOrderDto;
import khan.mobile.entity.Product_order;
import khan.mobile.entity.Products;
import khan.mobile.entity.Stores;
import khan.mobile.entity.Users;
import khan.mobile.repository.ProductOrderRepository;
import khan.mobile.repository.ProductRepository;
import khan.mobile.repository.StoreRepository;
import khan.mobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;


    // 상품 주문
    @Transactional
    public ProductOrderDto createOrder(ProductOrderDto productOrderDTO) {
        // 관련 엔티티 조회
        Users user = userRepository.findById(productOrderDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Products product = productRepository.findById(productOrderDTO.getProduct_id())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        Stores store = storeRepository.findById(productOrderDTO.getStore_id())
                .orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다."));

        // 주문 엔티티 생성
        Product_order productOrder = new Product_order(
                productOrderDTO.getQuantity(),
                productOrderDTO.getText(),
                user,
                store,
                product
        );

        // 주문 엔티티 저장
        Product_order createdOrder = productOrderRepository.save(productOrder);

        // DTO로 변환하여 반환
        return ProductOrderDto.productOrderDto(createdOrder);
    }

    // 상품 수정
    @Transactional
    public Product_order updateOrder(Long product_order_id, ProductOrderDto productOrderDto) {
        Product_order order = productOrderRepository.findById(productOrderDto.getProduct_id()).orElseThrow(() -> new EntityNotFoundException("주문 id = " + product_order_id));
        Users user = userRepository.findById(productOrderDto.getUser_id()).orElseThrow(() -> new EntityNotFoundException("유저 id = " + productOrderDto.getUser_id()));
        Stores store = storeRepository.findById(productOrderDto.getStore_id()).orElseThrow(() -> new EntityNotFoundException("가게 id = " + productOrderDto.getStore_id()));
        Products product = productRepository.findById(productOrderDto.getProduct_id()).orElseThrow(() -> new EntityNotFoundException("상품 id = " + productOrderDto.getStore_id()));

        return productOrderRepository.save(Product_order.builder()
                .product_order_quantity(order.getProduct_order_quantity())
                .product_order_text(order.getProduct_order_text())
                .user(user)
                .products(product)
                .stores(store)
                .build());

    }
}
