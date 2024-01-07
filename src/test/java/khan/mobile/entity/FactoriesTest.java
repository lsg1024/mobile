package khan.mobile.entity;

import jakarta.persistence.EntityManager;
import khan.mobile.repository.FactoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class FactoriesTest {

    @Autowired
    EntityManager em;

    @Autowired
    FactoryRepository factoryRepository;

    @Test
    void createFactory() {
        Factories factories = Factories.builder()
                .factory_name("테스트 공장")
                .build();

        factoryRepository.save(factories);

        em.flush();
        em.clear();

        Optional<Factories> findFactory = factoryRepository.findById(factories.getFactory_id());

        assertTrue(findFactory.isPresent(), "공장 아이디를 찾을 수 없음 : " + factories.getFactory_id());

        assertEquals(factories.getFactory_id(), findFactory.get().getFactory_id(), "공장 아이디가 존재합니다.");

    }
}