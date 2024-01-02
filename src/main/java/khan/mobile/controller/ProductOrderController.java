package khan.mobile.controller;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.entity.Product_order;
import khan.mobile.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @GetMapping("/hello")
    public String Hello() {
        return "Hello";
    }

    @PostMapping("/order")
    public ResponseEntity<ResponseDto> createOrder(@RequestBody ProductOrderDto productOrderDto) {
        productOrderService.createOrder(productOrderDto);
        return ResponseEntity.ok(new ResponseDto("success"));
    }


    static class ResponseDto {
        private String response;

        public ResponseDto(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }


}
