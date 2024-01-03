package khan.mobile.dto;

import khan.mobile.entity.Product_order;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDto {

    private int quantity;
    private String text;
    private Long product_id;
    private Long store_id;
    private Long user_id;

}
