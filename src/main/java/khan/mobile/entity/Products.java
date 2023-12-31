package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Products {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long product_id;
    private String product_name;
    private String product_color;
    private Float product_size;
    private Float product_weight;
    private String product_other;
    private String product_image;

}
