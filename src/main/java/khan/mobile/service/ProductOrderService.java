package khan.mobile.service;

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
import org.springframework.beans.factory.annotation.Autowired;
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
}
