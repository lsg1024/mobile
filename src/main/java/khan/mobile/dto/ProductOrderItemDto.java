package khan.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderItemDto {
    private Long product_id;
    private String color;
    private Float size;
    private String other;
    private int quantity;
}
