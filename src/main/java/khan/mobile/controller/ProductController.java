package khan.mobile.controller;

import jakarta.validation.Valid;
import khan.mobile.dto.ErrorResponse;
import khan.mobile.dto.ProductDto;
import khan.mobile.dto.ResponseDto;
import khan.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<ResponseDto> createProduct(@RequestHeader("userId") Long userId,
                                                     @RequestBody ProductDto productDto) {
        productService.createProduct(userId, productDto);
        return ResponseEntity.ok(new ResponseDto("생성 완료"));
    }

    @PostMapping("/product/update")
    public ResponseEntity<?> updateProduct(@RequestHeader("userId") Long userId,
                                           @RequestHeader("productId") Long productId,
                                           @Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(new ErrorResponse("상품 업데이트 실패", errors));
        }

        ProductDto result = productService.updateProduct(userId, productId, productDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/product")
    public Page<ProductDto> getAPIProductList(@PageableDefault(size = 9) Pageable pageable) {
        return productService.getProductList(pageable);
    }

    @GetMapping("/api/product/search")
    public Page<ProductDto> getProductSearchList(@RequestParam("productSearch") String productName, @PageableDefault(size = 9) Pageable pageable) {
        return productService.getSearchProductList(productName, pageable);
    }

    @GetMapping("/api/product/detail/{id}")
    public ProductDto getProductDetail(@PathVariable("id") Long id) {
        return productService.getProductDetail(id);
    }


}
