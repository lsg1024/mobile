package khan.mobile.controller;

import khan.mobile.dto.PrincipalDetails;
import khan.mobile.dto.ProductOrderDto;
import khan.mobile.dto.ProductOrderItemDetailDto;
import khan.mobile.dto.ProductOrderItemDto;
import khan.mobile.dto.response.CommonResponse;
import khan.mobile.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    // 상품 주문
    @PostMapping("/order")
    public ResponseEntity<CommonResponse> createOrder(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                      @RequestHeader("storeId") Long storeId,
                                                      @RequestBody ProductOrderDto productOrderDto) {
        productOrderService.createOrder(Long.parseLong(principalDetails.getId()), storeId, productOrderDto);
        return ResponseEntity.ok(new CommonResponse("제품 주문 완료"));
    }

    // 상품 수정
    @PostMapping("/order/update")
    public ResponseEntity<CommonResponse> updateOrder(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                       @RequestHeader("orderItemsId") Long orderItemsId,
                                                       @RequestBody ProductOrderItemDto productOrderItemDto) {
        productOrderService.updateOrder(Long.parseLong(principalDetails.getId()), orderItemsId, productOrderItemDto);
        return ResponseEntity.ok(new CommonResponse("주문 수정 완료"));
    }

    // 상품 삭제
    @PostMapping("/order/delete")
    public ResponseEntity<CommonResponse> deleteOrder(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                       @RequestHeader("orderItemsId") Long orderItemsId) {
        productOrderService.deleteOrder(Long.parseLong(principalDetails.getId()), orderItemsId);
        return ResponseEntity.ok(new CommonResponse("주문 삭제 완료"));
    }

    // 상품 리스트
    @GetMapping("orderItem/orderList")
    public ResponseEntity<List<ProductOrderItemDetailDto>> getOrderItemDetails(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<ProductOrderItemDetailDto> details = productOrderService.getOrderItemListDetails(Long.parseLong(principalDetails.getId()));
        return ResponseEntity.ok(details);
    }

}
