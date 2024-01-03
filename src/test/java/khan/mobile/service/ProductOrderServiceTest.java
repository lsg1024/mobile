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
    void createUser() {
        Users newUser = Users.builder()
                .id("테스트를 위한 아이디")
                .password("테스트를 위한 비밀번호")
                .role(Role.USER)
                .build();

        em.persist(newUser);

        assertNotNull(newUser);
        assertThat(newUser.getUser_id()).isEqualTo(7L);
        assertThat(newUser.getId()).isEqualTo("테스트를 위한 아이디");
        assertThat(newUser.getPassword()).isEqualTo("테스트를 위한 비밀번호");
        assertThat(newUser.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void createProduct() {
        Products newProduct = Products.builder()
                .product_name("테스트 상품")
                .product_color("금색")
                .product_weight(10.4f)
                .product_size(15f)
                .product_other("테스트 내용")
                .product_image("img/")
                .build();

        em.persist(newProduct);

        assertNotNull(newProduct);
        assertThat(newProduct.getProduct_id()).isEqualTo(7L);
        assertThat(newProduct.getProduct_name()).isEqualTo("테스트 상품");
        assertThat(newProduct.getProduct_weight()).isEqualTo(10.4f);
        assertThat(newProduct.getProduct_size()).isEqualTo(15f);
        assertThat(newProduct.getProduct_other()).isEqualTo("테스트 내용");
        assertThat(newProduct.getProduct_image()).isEqualTo("img/");
    }

    @Test
    void createStore() {
        Stores newStore = Stores.builder()
                .store_name("테스트 스토어")
                .build();

        em.persist(newStore);

        assertNotNull(newStore);
        assertThat(newStore.getStore_id()).isEqualTo(7L);
        assertThat(newStore.getStore_name()).isEqualTo("테스트 스토어")
    }

    @Test
    void ProductOrder() {
        //given
        Users user = Users.builder().user_id(0L).build();
        Products product = Products.builder().product_id(0L).build();
        Stores store = Stores.builder().store_id(0L).build();

        em.persist(user);
        em.persist(product);
        em.persist(store);

        ProductOrderDto productOrderDto = ProductOrderDto.builder()
                .quantity(5)
                .text("테스트 코드")
                .product_id(product.getProduct_id())
                .store_id(store.getStore_id())
                .user_id(user.getUser_id())
                .build();
        //when
        Product_order createOrder = productOrderService.createOrder(productOrderDto);


        //then
        assertNotNull(createOrder);
        assertEquals(productOrderDto.getQuantity(), createOrder.getProduct_order_quantity());
        assertEquals(productOrderDto.getText(), createOrder.getProduct_order_text());

    }

}