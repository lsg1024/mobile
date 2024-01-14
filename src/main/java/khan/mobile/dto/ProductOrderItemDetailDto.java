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
    private String productOrderItemColor;
    private Float productOrderItemSize;
    private String productOrderItemOther;
    private Integer productOrderItemQuantity;
    private Float productWeight;
    private String productName;
    private String productImage;

}
