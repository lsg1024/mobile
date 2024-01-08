package khan.mobile.service;

import khan.mobile.dto.StoreDto;
import khan.mobile.entity.Stores;
import khan.mobile.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    // 상점 저장
    public void saveStores(StoreDto storeDto) {

        Stores stores = Stores.builder()
                .store_name(storeDto.getStore_name())
                .build();

        Stores save = storeRepository.save(stores);

        StoreDto.storeName(save);

    }

    // 상점 리스트
//    public List<StoreDto> StoresList(StoreDto storeDto) {
//
//        Stores stores = Stores.builder()
//                .store_id(storeDto.getStore_id())
//                .store_name(storeDto.getStore_name())
//                .build();
//
//        List<Stores> storeList = storeRepository.findAll(stores);
//    }
}
