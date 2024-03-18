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
    public Products(Long productId, String productName, String productColor, Float productSize, Float productWeight, String productOther, List<ProductImage> productImage, Users user, Factories factory) {
        this.productId = productId;
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
            this.productName = productDto
        }
        this.productName = product_name;
        this.productColor = product_color;
        this.productSize = product_size;
        this.productWeight = product_weight;
        this.productOther = product_other;
        this.user = user;
        this.factory = factory;
    }
}
