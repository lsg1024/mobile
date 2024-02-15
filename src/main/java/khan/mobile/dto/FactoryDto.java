package khan.mobile.dto;

import jakarta.validation.constraints.NotBlank;
import khan.mobile.entity.Factories;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class FactoryDto {

    private Long factoryId;
    @NotBlank(message = "공장 이름 값이 비어 있습니다")
    private String factoryName;

    @Builder
    public static FactoryDto factoryDto(Factories factories) {
        return FactoryDto.builder()
                .factoryId(factories.getFactoryId())
                .factoryName(factories.getFactoryName())
                .build();
    }

}
