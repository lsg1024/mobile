package khan.mobile.entity;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.FactoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FactoriesTest {

    @Autowired
    EntityManager em;

    @Autowired
    FactoryRepository factoryRepository;

    @Test
    void createFactory() {

    }
}