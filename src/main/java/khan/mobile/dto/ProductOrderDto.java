package khan.mobile.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDto {

    private List<ProductOrderItemDto> orderItems;

}
