package khan.mobile.service;

import jakarta.persistence.EntityManager;
import khan.mobile.dto.ProductOrderDto;
import khan.mobile.entity.Product_order;
import khan.mobile.repository.ProductOrderRepository;
import khan.mobile.repository.StoreRepository;
import khan.mobile.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ProductOrderServiceTest {

    @Autowired EntityManager em;
    @Autowired ProductOrderRepository productOrderRepository;
    @Autowired ProductOrderService productOrderService;
    @Autowired UserRepository userRepository;
    @Autowired StoreRepository storeRepository;


//    @Test
//    void createOrderTest() {
//        // ProductOrderDTO 생성
//        ProductOrderDto testOrderDTO = ProductOrderDto.builder()
//                .user_id(1L)
//                .product_id(1L)
//                .store_id(1L)
//                .quantity(5)
//                .text("테스트 주문")
//                .build();
//
//        // 주문 생성 테스트
//        ProductOrderDto createdOrderDTO = productOrderService.createOrder(testOrderDTO);
//
//        // 검증
//        assertNotNull(createdOrderDTO);
//        assertEquals(testOrderDTO.getProduct_id(), createdOrderDTO.getProduct_id());
//        assertEquals(testOrderDTO.getText(), createdOrderDTO.getText());
//    }

    @Test
    void 전체주문리스트() {
        //when
        List<Product_order> findAll = productOrderRepository.findAll();

        //then
        assertNotNull(findAll);
    }

}