package khan.mobile.controller;

import khan.mobile.dto.ProductOrderDto;
import khan.mobile.dto.ProductOrderItemDto;
import khan.mobile.dto.ResponseDto;
import khan.mobile.entity.Product_orderItem;
import khan.mobile.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderService productOrderService;

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

    // 상품 삭제
    @PostMapping("/order/delete")
    public ResponseEntity<ResponseDto> deleteOrder(@RequestHeader("user_id") Long user_id,
                                                   @RequestHeader("orderItems_id") Long orderItems_id) {
        productOrderService.deleteOrder(user_id, orderItems_id);
        return ResponseEntity.ok(new ResponseDto("주문 삭제 완료"));
    }

    // 상품 리스트
    @GetMapping("orderItem/list")
    public ResponseEntity<List<ProductOrderItemDto>> itemList(@RequestParam("user_id") Long user_id) {
        List<Product_orderItem> orderItems = productOrderService.getUserOrderItems(user_id);

        List<ProductOrderItemDto> orderItemDtos = orderItems.stream()
                .map(item -> new ProductOrderItemDto(
                        item.getProduct_orderItem_id(),
                        item.getProduct_orderItem_color(),
                        item.getProduct_orderItem_size(),
                        item.getProduct_orderItem_other(),
                        item.getProduct_orderItem_quantity()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orderItemDtos);
    }

}
