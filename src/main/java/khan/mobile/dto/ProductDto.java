package khan.mobile.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import khan.mobile.entity.Factories;
import khan.mobile.entity.Products;
import khan.mobile.entity.Users;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    @NotBlank(message = "상품명이 비어 있습니다.")
    private String name;
    @NotBlank(message = "색상 값이 비어 있습니다.")
    private String color;
    @NotNull(message = "크기 값이 비어 있습니다.")
    @Min(value = 0, message = "크기는 0 이상이어야 합니다.")
    private Float size;
    @NotNull(message = "무게값이 비어 있습니다.")
    @Min(value = 0, message = "무게는 0 이상이어야 합니다.")
    private Float weight;
    private String other;
    private String image;
    private Users user;
    private Factories factory;

    @Builder
    public static ProductDto productDto(Products products) {
        return ProductDto.builder()
                .id(products.getProductId())
                .name(products.getProductName())
                .color(products.getProductColor())
                .size(products.getProductSize())
                .weight(products.getProductWeight())
                .other(products.getProductOther())
                .image(products.getProductImage())
                .build();
    }
}
