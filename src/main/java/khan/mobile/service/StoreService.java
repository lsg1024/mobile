package khan.mobile.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import khan.mobile.dto.StoreDto;
import khan.mobile.entity.Stores;
import khan.mobile.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .storeName(storeDto.getStoreName())
                .build();

        storeRepository.save(stores);

    }

    // 상점 리스트
    public Page<StoreDto> getStoreList(Pageable pageable) {
        return storeRepository.findAll(pageable)
                .map(StoreDto::storeDto);
    }

    // 상점 수정
    public void updateStores(String store_id, StoreDto storeDto) {

        Optional<Stores> store = storeRepository.findById(Long.parseLong(store_id));

        Stores stores = Stores.builder()
                .store_id(store.orElseThrow().getStore_id())
                .storeName(storeDto.getStoreName())
                .build();

        storeRepository.save(stores);
    }

    // 상점 검색
    public Page<StoreDto> getSearchResult(String storeName, Pageable pageable) {
        Page<Stores> result = storeRepository.findStoresByStoreNameContaining(storeName, pageable);

        return result.map(StoreDto::storeDto);
    }




}
