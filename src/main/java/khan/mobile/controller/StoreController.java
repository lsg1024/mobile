package khan.mobile.controller;

import khan.mobile.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StoreController {

    @GetMapping("/store/list")
    public ResponseEntity<StoreDto>
}
