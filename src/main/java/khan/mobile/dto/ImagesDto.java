package khan.mobile.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ImagesDto {
    private Long imageId;
    private String imageName;
    private String imagePath;

    @QueryProjection
    public ImagesDto(Long imageId, String imageName, String imagePath) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

}
