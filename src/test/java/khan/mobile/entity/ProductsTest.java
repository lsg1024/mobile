package khan.mobile.entity;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                .productName("테스트 상품2")
                .product_color("금색")
                .product_weight(10.4f)
                .product_size(15f)
                .product_other("테스트 내용")
                .product_image("img/")
                .build();


        productRepository.save(newProduct);

        Products findProduct = productRepository.findById(newProduct.getProductId()).orElse(null);
        assertNotNull(findProduct);
        assertNotNull(findProduct.getProductId());
        assertEquals(newProduct.getProductId(), findProduct.getProductId());

    }

}