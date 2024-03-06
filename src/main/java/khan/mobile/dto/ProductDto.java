package khan.mobile.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import khan.mobile.entity.Factories;
import khan.mobile.entity.ProductImage;
import khan.mobile.entity.Products;
import khan.mobile.entity.Users;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String color;
    private Float size;
    private Float weight;
    private String other;
    private List<ProductImage> images;
    private Users user;
    private Factories factory;

    @Getter @Setter
    public static class Create {
        private Long id;
        @NotBlank(message = "상품명이 비어 있습니다")
        private String name;
        @NotBlank(message = "색상 값이 비어 있습니다")
        private String color;
        @NotNull(message = "크기 값이 비어 있습니다")
        @Min(value = 0, message = "크기는 0 이상이어야 합니다")
        private Float size;
        @NotNull(message = "무게값이 비어 있습니다")
        @Min(value = 0, message = "무게는 0 이상이어야 합니다")
        private Float weight;
        private String other;
        private List<ProductImage> images;
        private Users user;
        private Factories factory;

    }

    @Data
    public static class ProductCondition {
        private String productName;
    }

    @Getter @Setter
    public static class ProductDataSet {
        private Long id;
        private String name;
        private String color;
        private Float size;
        private Float weight;
        private String other;
        private String imagePath;
        private Long userId;
        private Long factoryId;

        @QueryProjection
        public ProductDataSet(Long id, String name, String color, Float size, Float weight, String other, Long userId, Long factoryId) {
            this.id = id;
            this.name = name;
            this.color = color;
            this.size = size;
            this.weight = weight;
            this.other = other;
            this.userId = userId;
            this.factoryId = factoryId;
        }
    }

    @Builder
    public static ProductDto productDto(Products products) {
        return ProductDto.builder()
                .id(products.getProductId())
                .name(products.getProductName())
                .color(products.getProductColor())
                .size(products.getProductSize())
                .weight(products.getProductWeight())
                .other(products.getProductOther())
                .images(products.getProductImage())
                .build();
    }
}
