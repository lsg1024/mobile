package khan.mobile.entity;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class StoresTest {

    @Autowired
    EntityManager em;

    @Autowired
    StoreRepository storeRepository;

    @Test
    void createStore() {
        Stores newStore = Stores.builder()
                .store_name("테스트 스토어")
                .build();

        storeRepository.save(newStore);

        em.flush();
        em.clear();

        Stores findStore = storeRepository.findById(newStore.getStore_id()).orElse(null);
        assertNotNull(findStore);
        assertNotNull(findStore.getStore_id());
        assertEquals(newStore.getStore_id(), findStore.getStore_id());
    }

}