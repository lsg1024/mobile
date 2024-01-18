package khan.mobile.dto;

import khan.mobile.entity.Factories;
import khan.mobile.entity.Products;
import khan.mobile.entity.Users;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String name;
    private String color;
    private Float size;
    private Float weight;
    private String other;
    private String image;
    private Users user;
    private Factories factory;

    @Builder
    public static ProductDto productDto(Products products) {
        return ProductDto.builder()
                .name(products.getProduct_name())
                .color(products.getProduct_color())
                .size(products.getProduct_size())
                .weight(products.getProduct_weight())
                .other(products.getProduct_other())
                .image(products.getProduct_image())
                .build();
    }
}
