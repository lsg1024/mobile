package khan.mobile.dto;

import khan.mobile.entity.Stores;
import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

    private Long store_id;
    private String store_name;

    @Builder
    public static StoreDto storeDto(Stores stores) {
        return StoreDto.builder()
                .store_id(stores.getStore_id())
                .store_name(stores.getStoreName())
                .build();
    }

}
