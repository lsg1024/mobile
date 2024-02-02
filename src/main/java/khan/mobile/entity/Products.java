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
    @Column(name = "product_id")
    private Long product_id;
    private String productName;
    private String product_color;
    private Float product_size;
    private Float product_weight;
    private String product_other;
    private String product_image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factory_id")
    private Factories factory;

    @OneToMany(mappedBy = "products")
    private List<Product_orderItem> product_orderItem;

    @Builder
    public Products(Long product_id, String productName, String product_color, Float product_size, Float product_weight, String product_other, String product_image, Users user, Factories factory) {
        this.product_id = product_id;
        this.productName = productName;
        this.product_color = product_color;
        this.product_size = product_size;
        this.product_weight = product_weight;
        this.product_other = product_other;
        this.product_image = product_image;
        this.user = user;
        this.factory = factory;
    }

    public void updateProduct(String product_name, String product_color, Float product_size, Float product_weight, String product_other) {
        this.productName = product_name;
        this.product_color = product_color;
        this.product_size = product_size;
        this.product_weight = product_weight;
        this.product_other = product_other;
    }
}
