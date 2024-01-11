package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product_orderItem {

    @Id @GeneratedValue
    @Column(name = "product_orderItem_id")
    private Long product_orderItem_id;

    private String product_orderItem_name;
    private String product_orderItem_color;
    private Float product_orderItem_size;
    private Float product_orderItem_weight;
    private String product_orderItem_other;

    @ManyToOne
    @JoinColumn(name = "product_order_id")
    private Product_order product_order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

}
