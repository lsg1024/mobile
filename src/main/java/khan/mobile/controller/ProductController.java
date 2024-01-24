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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<ResponseDto> createProduct(@RequestHeader("user_id") Long user_id,
                                                     @RequestBody ProductDto productDto) {
        productService.createProduct(user_id, productDto);
        return ResponseEntity.ok(new ResponseDto("생성 완료"));
    }

    @PostMapping("/product/update")
    public ResponseEntity<?> updateProduct(@RequestHeader("user_id") Long user_id,
                                                    @RequestHeader("product_id") Long product_id,
                                                    @Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(new ErrorResponse("상품 업데이트 실패", errors));
        }

        ProductDto result = productService.updateProduct(user_id, product_id, productDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/product")
    public String getProductList(Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<ProductDto> productPage = productService.getProductList(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", productPage);
        return "productPages/product";
    }

    @GetMapping("/product/detail/{id}")
    public String getProductDetail(@PathVariable("id") Long id, Model model) {
        log.info("Product Detail request for ID: " + id);
        ProductDto product = productService.getProductDetail(id);
        model.addAttribute("product", product);
        return "productPages/productDetail";
    }
}
