package khan.mobile.entity;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        ProductImage productImage = ProductImage.builder()
                .imagePath("/img")
                .build();

        Products newProduct = Products.builder()
                .productName("테스트 상품2")
                .productColor("금색")
                .productWeight(10.4f)
                .productSize(15f)
                .productOther("테스트 내용")
                .productImage((List<ProductImage>) productImage)
                .build();


        productRepository.save(newProduct);

        Products findProduct = productRepository.findById(newProduct.getProductId()).orElse(null);
        assertNotNull(findProduct);
        assertNotNull(findProduct.getProductId());
        assertEquals(newProduct.getProductId(), findProduct.getProductId());

    }

}