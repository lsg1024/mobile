package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

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

    @Builder
    public ProductImage(Long imageId, String imageName, String imageOriginName, String imagePath, String firstImagePath, Products products) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageOriginName = imageOriginName;
        this.imagePath = imagePath;
        this.firstImagePath = firstImagePath;
        this.products = products;
    }

    public void setProduct(Products product) {
        this.products = product;
        if (!product.getProductImage()
                .contains(this)) {
            product.getProductImage().add(this);
        }
    }
}
