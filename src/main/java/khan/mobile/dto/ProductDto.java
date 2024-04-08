package khan.mobile.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import khan.mobile.entity.Factories;
import khan.mobile.entity.ProductImage;
import khan.mobile.entity.Users;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String color;
    private Float size;
    private Float weight;
    private String other;
    private List<ImagesDto> images;
    private Long userId;
    private Long factoryId;

    @Getter @Setter
    public static class Create {

        @NotBlank(message = "상품명이 비어 있습니다")
        private String name;

        @NotNull(message = "시리얼번호가 비어 있습니다")
        private Long serialNumber;

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

        @NotNull(message = "공장을 선택해주세요")
        private Long factoryId;
    }

    @Getter @Setter
    public static class Update {

        @NotBlank(message = "상품명이 비어 있습니다")
        private String name;

        @NotNull(message = "시리얼번호가 비어 있습니다")
        private Long serialNumber;

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

        @NotNull(message = "공장 선택을 해주세요")
        private Long factoryId;
    }

    @NoArgsConstructor
    @Builder
    @Getter
    public static class Detail {
        private Long id;
        private String name;
        private Long serialNumber;
        private String color;
        private Float size;
        private Float weight;
        private String other;
        private List<ImagesDto> images;
        private Long userId;
        private Long factoryId;
        private String factoryName;

        @QueryProjection
        public Detail(Long id, String name, Long serialNumber, String color, Float size, Float weight, String other, List<ImagesDto> images, Long userId, Long factoryId, String factoryName) {
            this.id = id;
            this.name = name;
            this.serialNumber = serialNumber;
            this.color = color;
            this.size = size;
            this.weight = weight;
            this.other = other;
            this.images = images;
            this.userId = userId;
            this.factoryId = factoryId;
            this.factoryName = factoryName;
        }
    }

    @Getter @Setter
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
}
