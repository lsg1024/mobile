package khan.mobile.service;

import jakarta.persistence.EntityManager;
import khan.mobile.entity.Products;
import khan.mobile.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    void SearchProduct() {
        String searchData = "상품1";

        List<Products> result = productRepository.findByProductNameContaining(searchData);

        System.out.println("상품 갯수는 " + result.size());
    }

}