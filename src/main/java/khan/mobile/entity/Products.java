package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.dto.ProductDto;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE Products SET DELETED = true where PRODUCT_ID")
@SQLRestriction("deleted = false")
public class Products extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;
    private String productName;
    private String productColor;
    private Float productSize;
    private Float productWeight;
    private String productOther;
    private Long productSerialNumber;
    private boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factoryId")
    private Factories factory;

    @OneToMany(mappedBy = "products")
    private List<ProductOrderItem> productOrderItem;

    @OneToMany(mappedBy = "products")
    private List<ProductImage> productImage;

    @Builder
    public Products(String productName, Long productSerialNumber , String productColor, Float productSize, Float productWeight, String productOther, List<ProductImage> productImage, Users user, Factories factory) {
        this.productSerialNumber = productSerialNumber;
        this.productName = productName;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productWeight = productWeight;
        this.productOther = productOther;
        this.productImage = productImage;
        this.user = user;
        this.factory = factory;
    }

    public void updateProduct(ProductDto.Update productDto) {

        if (productDto.getName() != null) {
            this.productName = productDto.getName();
        }
        if (productDto.getColor() != null) {
            this.productColor = productDto.getColor();
        }
        if (productDto.getSize() != null) {
            this.productSize = productDto.getSize();
        }
        if (productDto.getWeight() != null) {
            this.productWeight = productDto.getWeight();
        }
        if (productDto.getOther() != null) {
            this.productOther = productDto.getOther();
        }
    }

    public void setFactory(Factories factory) {
        this.factory = factory;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
