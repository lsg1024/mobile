package khan.mobile.dto;

import khan.mobile.entity.Stores;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

    private Long storeId;
    private String storeName;

    @Builder
    public static StoreDto storeDto(Stores stores) {
        return StoreDto.builder()
                .storeId(stores.getStore_id())
                .storeName(stores.getStoreName())
                .build();
    }

}
