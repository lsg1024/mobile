package khan.mobile.controller;

import khan.mobile.dto.ProductDto;
import khan.mobile.dto.ResponseDto;
import khan.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<ResponseDto> createProduct(@RequestHeader("user_id") Long user_id,
                                                     @RequestHeader("factory_id") Long factory_id,
                                                     @RequestBody ProductDto productDto) {
        productService.createProduct(user_id, factory_id, productDto);
        return ResponseEntity.ok(new ResponseDto("생성 완료"));
    }

}
