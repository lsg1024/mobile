package khan.mobile.controller;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.entity.Product_order;
import khan.mobile.service.ProductOrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @GetMapping("/hello")
    public String Hello() {
        return "Hello";
    }

    // 상품 주문
    @PostMapping("/order")
    public ResponseEntity<ResponseDto> createOrder(@RequestHeader("user_id") Long user_id,
                                                   @RequestHeader("store_id") Long store_id,
                                                   @RequestBody ProductOrderDto productOrderDto) {
        productOrderService.createOrder(user_id, store_id, productOrderDto);

        return ResponseEntity.ok(new ResponseDto("제품 주문 완료"));
    }

    // 상품 수정
//    @PostMapping("/order/{orderId}")
//    public ResponseEntity<ProductOrderDto> updateOrder(@PathVariable("orderId") Long orderId, @RequestBody ProductOrderDto productOrderDto) {
//        productOrderService.updateOrder(orderId, productOrderDto);
//        return ResponseEntity.ok(productOrderDto);
//    }


    @Getter @Setter
    static class ResponseDto {
        private String response;
        public ResponseDto(String response) {
            this.response = response;
        }
    }


}
