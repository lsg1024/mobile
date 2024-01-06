package khan.mobile.entity;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductsTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProductRepository productRepository;

    @Test
    void createProduct() {
        Products newProduct = Products.builder()
                .product_name("테스트 상품2")
                .product_color("금색")
                .product_weight(10.4f)
                .product_size(15f)
                .product_other("테스트 내용")
                .product_image("img/")
                .build();


        productRepository.save(newProduct);

        em.flush();
        em.clear();

        Products findProduct = productRepository.findById(newProduct.getProduct_id()).orElse(null);
        assertNotNull(findProduct);
        assertNotNull(findProduct.getProduct_id());
        assertEquals(newProduct.getProduct_id(), findProduct.getProduct_id());

    }

}