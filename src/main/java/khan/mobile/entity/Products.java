package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Products extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;
    private String productName;
    private String productColor;
    private Float productSize;
    private Float productWeight;
    private String productOther;

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

    public void updateProduct(String product_name, String product_color, Float product_size, Float product_weight, String product_other) {
        this.productName = product_name;
        this.productColor = product_color;
        this.productSize = product_size;
        this.productWeight = product_weight;
        this.productOther = product_other;
    }
}
