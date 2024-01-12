package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product_orderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_orderItem_id")
    private Long product_orderItem_id;

    private String product_orderItem_color;
    private Float product_orderItem_size;
    private String product_orderItem_other;
    private int product_orderItem_quantity;

    @ManyToOne
    @JoinColumn(name = "product_order_id")
    private Product_order product_order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @Builder
    public Product_orderItem(Long product_orderItem_id, String product_orderItem_color, Float product_orderItem_size, String product_orderItem_other, int product_orderItem_quantity, Product_order product_order, Products products) {
        this.product_orderItem_id = product_orderItem_id;
        this.product_orderItem_color = product_orderItem_color;
        this.product_orderItem_size = product_orderItem_size;
        this.product_orderItem_other = product_orderItem_other;
        this.product_orderItem_quantity = product_orderItem_quantity;
        this.product_order = product_order;
        this.products = products;
    }

    public void updateOrderItem(String color, Float size, String other, int quantity) {
        this.product_orderItem_color = color;
        this.product_orderItem_size = size;
        this.product_orderItem_other = other;
        this.product_orderItem_quantity = quantity;
    }
}
