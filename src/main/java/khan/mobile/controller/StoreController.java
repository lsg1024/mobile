package khan.mobile.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import khan.mobile.dto.StoreDto;
import khan.mobile.entity.QStores;
import khan.mobile.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/stores")
    public String StoreList(Model model, Pageable pageable) {
        Page<StoreDto> storePage = storeService.getStoreList(pageable);
        model.addAttribute("stores", storePage.getContent());
        model.addAttribute("page", storePage);
        return "storeList";
    }



}
