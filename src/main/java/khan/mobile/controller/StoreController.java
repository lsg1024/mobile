package khan.mobile.controller;

import khan.mobile.dto.response.CommonResponse;
import khan.mobile.dto.response.SuccessResponse;
import khan.mobile.dto.StoreDto;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/api/stores")
    public Page<StoreDto> StoreList(Pageable pageable) {
        return storeService.getStoreList(pageable);
    }

    @GetMapping("/api/stores/search")
    public Page<StoreDto> getSearchStoresList(@RequestParam("storeSearch") String storeName, Pageable pageable) {
        return storeService.getSearchResult(storeName, pageable);
    }

    @PostMapping("/api/stores/save")
    public ResponseEntity<CommonResponse> saveStores(@RequestBody List<StoreDto> dataList) {

        for (StoreDto data : dataList) {
            StoreDto stores = StoreDto.builder()
                    .storeName(data.getStoreName())
                    .build();
            storeService.saveStores(stores);
        }

        return ResponseEntity.ok(new CommonResponse("데이터 저장 성공"));

    }

    @PostMapping("api/stores/update")
    public ResponseEntity<CommonResponse> StoreUpdate(@RequestParam("storeId") String storeId, @Validated @RequestBody StoreDto storeDto) {
        storeService.updateStores(storeId, storeDto);
        return ResponseEntity.ok(new CommonResponse("수정 완료"));
    }

}
