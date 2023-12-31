package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


}
