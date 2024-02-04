package khan.mobile.controller;

import khan.mobile.dto.ResponseDto;
import khan.mobile.dto.StoreDto;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/store")
    public String StoreList(Model model, Pageable pageable) {
        Page<StoreDto> storePage = storeService.getStoreList(pageable);
        model.addAttribute("stores", storePage.getContent());
        model.addAttribute("page", storePage);
        return "storePages/storeList";
    }

    @PostMapping("/stores/update")
    public ResponseEntity<ResponseDto> StoreUpdate(@RequestParam("storeId") String storeId, @Validated @RequestBody StoreDto storeDto) {
        storeService.updateStores(storeId, storeDto);
        return ResponseEntity.ok(new ResponseDto("수정 완료"));
    }



}
