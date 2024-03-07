package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private Long imageId;
    private String imageName;
    private String imageOriginName;
    private String imagePath;
    private String firstImagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Products products;

}
