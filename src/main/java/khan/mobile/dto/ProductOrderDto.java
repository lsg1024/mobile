package khan.mobile.dto;

import khan.mobile.entity.Product_order;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDto {

    private List<ProductOrderItemDto> orderItems;

}
