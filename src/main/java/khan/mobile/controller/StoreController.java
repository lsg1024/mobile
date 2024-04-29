package khan.mobile.controller;

import jakarta.validation.Valid;
import khan.mobile.dto.StoreDto;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/stores")
    public Page<StoreDto> StoreList(Pageable pageable) {
        return storeService.getStoreList(pageable);
    }

    @PostMapping("/store")
    public ResponseEntity<CommonResponse> createStore(@Valid @RequestBody StoreDto.Create createDto,
                                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(new CommonResponse("가게 생성 실패"));
        }

        storeService.createStore(createDto);

        return ResponseEntity.ok().body(new CommonResponse("가게 생성 성공"));

    }

    @PostMapping("/stores")
    public ResponseEntity<CommonResponse> saveStores(@RequestBody List<StoreDto.Create> dataList) {

        for (StoreDto.Create data : dataList) {
            StoreDto.Create stores = StoreDto.Create.builder()
                    .storeName(data.getStoreName())
                    .build();

            storeService.saveStores(stores);
        }

        return ResponseEntity.ok(new CommonResponse("데이터 저장 성공"));

    }

    @PostMapping("/stores/update")
    public ResponseEntity<CommonResponse> StoreUpdate(@RequestParam("storeId") String storeId, @Validated @RequestBody StoreDto storeDto) {
        storeService.updateStores(storeId, storeDto);
        return ResponseEntity.ok(new CommonResponse("수정 완료"));
    }

    @GetMapping("/stores/search")
    public Page<StoreDto> getSearchStoresList(@RequestParam("storeSearch") String storeName, Pageable pageable) {
        return storeService.getSearchResult(storeName, pageable);
    }

}
