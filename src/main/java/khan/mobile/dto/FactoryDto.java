package khan.mobile.dto;

import jakarta.validation.constraints.NotBlank;
import khan.mobile.entity.Factories;
import lombok.*;

@AllArgsConstructor
@Getter
public class FactoryDto {

    private Long factoryId;
    private String factoryName;

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Create {
        @NotBlank(message = "공장 이름 값이 비어 있습니다")
        private String factoryName;

    }

    @Getter @Setter
    public static class Update {
        private Long factoryId;
        private String factoryName;

    }
    public static FactoryDto factoryDto(Factories factories) {
        return new FactoryDto(
                factories.getFactoryId(),
                factories.getFactoryName());
    }

}
