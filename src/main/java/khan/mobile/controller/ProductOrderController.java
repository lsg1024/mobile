package khan.mobile.controller;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.dto.ProductOrderItemDto;
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
    @PostMapping("/order/update")
    public ResponseEntity<ResponseDto> updateOrder(@RequestHeader("user_id") Long user_id,
                                                   @RequestHeader("orderItems_id") Long orderItems_id,
                                                   @RequestBody ProductOrderItemDto productOrderItemDto) {
        productOrderService.updateOrder(user_id, orderItems_id, productOrderItemDto);
        return ResponseEntity.ok(new ResponseDto("주문 수정 완료"));
    }


    @Getter @Setter
    static class ResponseDto {
        private String response;
        public ResponseDto(String response) {
            this.response = response;
        }
    }


}
