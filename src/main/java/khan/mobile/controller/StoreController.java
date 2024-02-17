package khan.mobile.controller;

import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.StoreDto;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
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
    public ResponseEntity<ResponseDto> saveStores(@RequestBody List<StoreDto> dataList) {

        for (StoreDto data : dataList) {
            StoreDto stores = StoreDto.builder()
                    .storeName(data.getStoreName())
                    .build();
            storeService.saveStores(stores);
        }

        return ResponseEntity.ok(new ResponseDto("데이터 저장 성공"));

    }

    @PostMapping("api/stores/update")
    public ResponseEntity<ResponseDto> StoreUpdate(@RequestParam("storeId") String storeId, @Validated @RequestBody StoreDto storeDto) {
        storeService.updateStores(storeId, storeDto);
        return ResponseEntity.ok(new ResponseDto("수정 완료"));
    }

}
