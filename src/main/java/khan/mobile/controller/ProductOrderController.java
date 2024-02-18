package khan.mobile.controller;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.dto.ProductOrderItemDetailDto;
import khan.mobile.dto.ProductOrderItemDto;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.dto.response.SuccessResponse;
import khan.mobile.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    // 상품 주문
    @PostMapping("/order")
    public ResponseEntity<CommonResponse> createOrder(@RequestHeader("userId") Long userId,
                                                      @RequestHeader("storeId") Long storeId,
                                                      @RequestBody ProductOrderDto productOrderDto) {
        productOrderService.createOrder(userId, storeId, productOrderDto);
        return ResponseEntity.ok(new CommonResponse("제품 주문 완료"));
    }

    // 상품 수정
    @PostMapping("/order/update")
    public ResponseEntity<CommonResponse> updateOrder(@RequestHeader("userId") Long userId,
                                                       @RequestHeader("orderItemsId") Long orderItemsId,
                                                       @RequestBody ProductOrderItemDto productOrderItemDto) {
        productOrderService.updateOrder(userId, orderItemsId, productOrderItemDto);
        return ResponseEntity.ok(new CommonResponse("주문 수정 완료"));
    }

    // 상품 삭제
    @PostMapping("/order/delete")
    public ResponseEntity<CommonResponse> deleteOrder(@RequestHeader("userId") Long userId,
                                                       @RequestHeader("orderItemsId") Long orderItemsId) {
        productOrderService.deleteOrder(userId, orderItemsId);
        return ResponseEntity.ok(new CommonResponse("주문 삭제 완료"));
    }

    // 상품 리스트
    @GetMapping("orderItem/orderList")
    public ResponseEntity<List<ProductOrderItemDetailDto>> getOrderItemDetails(@RequestHeader("userId") Long userId) {
        List<ProductOrderItemDetailDto> details = productOrderService.getOrderItemListDetails(userId);
        return ResponseEntity.ok(details);
    }

}
