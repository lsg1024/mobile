package khan.mobile.controller;

import khan.mobile.dto.ProductDto;
import khan.mobile.dto.ResponseDto;
import khan.mobile.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<ResponseDto> createProduct(@RequestHeader("user_id") Long user_id,
                                                     @RequestBody ProductDto productDto) {
        productService.createProduct(user_id, productDto);
        return ResponseEntity.ok(new ResponseDto("생성 완료"));
    }

    @PostMapping("/product/update")
    public ResponseEntity<ResponseDto> updateProduct(@RequestHeader("user_id") Long user_id,
                                                     @RequestHeader("product_id") Long product_id,
                                                     @RequestBody ProductDto productDto) {
        productService.updateProduct(user_id, product_id, productDto);
        return ResponseEntity.ok(new ResponseDto("수정 완료"));
    }

    @GetMapping("/product")
    public String getProductList(Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<ProductDto> productPage = productService.getProductList(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", productPage);
        return "htmlpages/product";
    }
}
