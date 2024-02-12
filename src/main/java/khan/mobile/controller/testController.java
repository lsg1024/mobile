package khan.mobile.controller;

import khan.mobile.dto.ProductDto;
import khan.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class testController {
    private final ProductService productService;
    @GetMapping("api/product")
    public Page<ProductDto> getAPIProductList(@PageableDefault(size = 9) Pageable pageable) {
        return productService.getProductList(pageable);
    }

    @GetMapping("api/product/search")
    public Page<ProductDto> getProductSearchList(@RequestParam("productSearch") String productName, @PageableDefault(size = 9) Pageable pageable) {
        return productService.getSearchProductList(productName, pageable);
    }

    @GetMapping("api/product/detail/{id}")
    public ProductDto getProductDetail(@PathVariable("id") Long id) {
        return productService.getProductDetail(id);
    }
}
