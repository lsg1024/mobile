package khan.mobile.service;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import khan.mobile.dto.StoreDto;
import khan.mobile.entity.QStores;
import khan.mobile.entity.Stores;
import khan.mobile.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final JPAQueryFactory queryFactory;

    public StoreService(StoreRepository storeRepository, EntityManager em) {
        this.storeRepository = storeRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 상점 저장
    public void saveStores(StoreDto storeDto) {

        Stores stores = Stores.builder()
                .store_name(storeDto.getStore_name())
                .build();

        Stores save = storeRepository.save(stores);

        StoreDto.storeDto(save);

    }

    // 상점 리스트
    public Page<StoreDto> getStoreList(Pageable pageable) {
        return storeRepository.findAll(pageable)
                .map(StoreDto::storeDto);
    }

    // 상점 수정
    public void updateStores(Long store_id, String storeName) {

        Optional<Stores> store = storeRepository.findById(store_id);

        Stores stores = Stores.builder()
                .store_id(store.get().getStore_id())
                .store_name(storeName)
                .build();

        storeRepository.save(stores);
    }
}
