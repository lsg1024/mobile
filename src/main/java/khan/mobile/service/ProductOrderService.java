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
    public Product_order createOrder(ProductOrderDto productOrderDto) {

        Users user = userRepository.findById(productOrderDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("유저 값이 비어있습니다."));
        Stores store = storeRepository.findById(productOrderDto.getStore_id())
                .orElseThrow(() -> new RuntimeException("가게 정보가 없습니다."));
        Products product = productRepository.findById(productOrderDto.getProduct_id())
                .orElseThrow(() -> new RuntimeException("상품 정보가 없습니다"));

        Product_order order = Product_order.createOrder(
                productOrderDto.getQuantity(),
                productOrderDto.getText(),
                user,
                store,
                product
        );

        return productOrderRepository.save(order);
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
