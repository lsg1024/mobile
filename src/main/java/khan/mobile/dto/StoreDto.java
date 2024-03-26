package khan.mobile.dto;

import jakarta.validation.constraints.NotBlank;
import khan.mobile.entity.Stores;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

    private Long storeId;
    private String storeName;

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Create {
        @NotBlank(message = "상점 이름 값이 비어 있습니다")
        private String storeName;
    }

    @Getter @Setter
    public static class Update {
        private Long storeId;
        private String storeName;
    }

    @Builder
    public static StoreDto storeDto(Stores stores) {
        return StoreDto.builder()
                .storeId(stores.getStore_id())
                .storeName(stores.getStoreName())
                .build();
    }

}
