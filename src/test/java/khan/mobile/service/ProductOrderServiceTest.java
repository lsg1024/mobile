package khan.mobile.service;

import jakarta.persistence.EntityManager;
import khan.mobile.dto.ProductOrderDto;
import khan.mobile.entity.*;
import khan.mobile.repository.ProductOrderRepository;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductOrderServiceTest {

    @Autowired EntityManager em;
    @Autowired ProductOrderRepository productOrderRepository;
    @Autowired ProductOrderService productOrderService;

    @Test
    void ProductOrder() {
        //given
        Users newUser = Users.builder().id("테스트를 위한 아이디").password("테스트를 위한 비밀번호").role(Role.USER).build();

        Products newProduct = Products.builder().product_name("테스트 상품").product_color("금색").product_weight(10.4f).product_size(15f).product_other("테스트 내용").product_image("img/").build();

        Stores newStore = Stores.builder().store_name("테스트 스토어").build();

        em.persist(newUser);
        em.persist(newProduct);
        em.persist(newStore);

        ProductOrderDto productOrderDto = ProductOrderDto.builder()
                .quantity(5)
                .text("테스트 코드")
                .product_id(newProduct.getProduct_id())
                .store_id(newStore.getStore_id())
                .user_id(newUser.getUser_id())
                .build();
        //when
        Product_order createOrder = productOrderService.createOrder(productOrderDto);


        //then
        assertNotNull(createOrder);
        assertEquals(productOrderDto.getQuantity(), createOrder.getProduct_order_quantity());
        assertEquals(productOrderDto.getText(), createOrder.getProduct_order_text());

    }

}