package khan.mobile.dto;

import khan.mobile.entity.Product_order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDto {

    private Long product_order_id;
    private int quantity;
    private String text;
    private Long product_id;
    private Long store_id;
    private Long user_id;

    @Builder
    public static ProductOrderDto productOrderDto(Product_order product_order) {
        return ProductOrderDto.builder()
                .product_order_id(product_order.getProduct_order_id())
                .user_id(product_order.getUser().getUser_id())
                .product_id(product_order.getProducts().getProduct_id())
                .store_id(product_order.getStores().getStore_id())
                .quantity(product_order.getProduct_order_quantity())
                .text(product_order.getProduct_order_text())
                .build();
    }
}
