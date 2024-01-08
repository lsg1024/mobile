package khan.mobile.service;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    StoreRepository storeRepository;

    @Autowired StoreService storeService;

    @Test
    void saveStores() {
        //given

        //when

        //then
    }
}