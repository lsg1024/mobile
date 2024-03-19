package khan.mobile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.dto.ProductDto;
import khan.mobile.oauth2.CustomOAuth2User;
import khan.mobile.repository.ProductRepository;
import khan.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<CommonResponse> createProduct(
            @RequestParam("product") String productStr,
            @RequestParam("images") List<MultipartFile> images,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) throws JsonProcessingException {

        ProductDto.Create productDto = new ObjectMapper().readValue(productStr, ProductDto.Create.class);

        productService.createProduct(Long.valueOf(customOAuth2User.getId()), productDto, images);
        return ResponseEntity.ok(new CommonResponse("생성 완료"));
    }

    @PostMapping("/product/update")
    public ResponseEntity<CommonResponse> updateProduct(
            @RequestHeader("productId") Long productId,
            @RequestParam(value = "product", required = false) String productStr,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User) throws IOException {

        ProductDto.Update productDto;

        try {
            productDto = new ObjectMapper().readValue(productStr, ProductDto.Update.class);
            log.info("updateProduct productDto = {}",productDto);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new CommonResponse("상품 정보 파싱 실패"));
        }

        productService.updateProduct(Long.valueOf(customOAuth2User.getId()), productId, productDto, images);
        return ResponseEntity.ok().body(new CommonResponse("업데이트 완료"));
    }


    @GetMapping("/products")
    public Page<ProductDto.ProductDataSet> getProductList(ProductDto.ProductCondition condition, @PageableDefault(size = 9) Pageable pageable) {
        return productService.getProductList(condition, pageable);
    }

    @GetMapping("/products/search")
    public Page<ProductDto.ProductDataSet> getProductSearchList(@RequestParam("productSearch") String getProductName, @PageableDefault(size = 9) Pageable pageable) {
        ProductDto.ProductCondition condition = new ProductDto.ProductCondition();
        condition.setProductName(getProductName);
        return productService.getProductList(condition, pageable);
    }

    @GetMapping("/product/detail/{id}")
    public ProductDto.Detail getProductDetail(@PathVariable("id") Long id) {
        return productService.getProductDetail(id);
    }


}
