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
    private String product_name;
    private String product_color;
    private Float product_size;
    private Float product_weight;
    private String product_other;
    private String product_image;

    @OneToMany(mappedBy = "products")
    private List<Product_registration> product_registration;

    @OneToMany(mappedBy = "products")
    private List<Product_order> productOrders;

    @Builder
    public Products(Long product_id, String product_name, String product_color, Float product_size, Float product_weight, String product_other, String product_image, List<Product_registration> product_registration, List<Product_order> productOrders) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_color = product_color;
        this.product_size = product_size;
        this.product_weight = product_weight;
        this.product_other = product_other;
        this.product_image = product_image;
        this.product_registration = product_registration;
        this.productOrders = productOrders;
    }
}
