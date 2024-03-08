package khan.mobile.service;

import jakarta.persistence.EntityManager;
import khan.mobile.entity.Factories;
import khan.mobile.repository.FactoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class FactoryServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    FactoryRepository factoryRepository;

    @Autowired
    FactoryService factoryService;

    @Test
    @DisplayName("공장 삭제 soft delete")
    void delete() {

        Factories factories = Factories.builder()
                .factoryName("테스트 공장")
                .build();

        Factories factory_save = factoryRepository.save(factories);

        assertThat(factory_save.getFactoryId()).isNotNull();
        assertThat(factory_save.isDeleted()).isFalse();

        factoryRepository.delete(factory_save);

        em.flush();

        Optional<Factories> afterDelete = factoryRepository.findById(factory_save.getFactoryId());
        assertThat(afterDelete).isNotEmpty();
        assertThat(afterDelete.get().isDeleted()).isTrue();
    }

}
