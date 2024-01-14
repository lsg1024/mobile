package khan.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderItemDetailDto {

    private Long productOrderItemId;
    private String color;
    private Float size;
    private String other;
    private Integer quantity;
    private Float weight;
    private String productName;
    private String productImage;

}
