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


    }

}