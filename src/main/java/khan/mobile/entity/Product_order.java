package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseEntity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product_order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_id")
    private Long product_order_id;
    private int product_order_quantity;
    private String product_order_text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Stores stores;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @Builder
    public Product_order(int product_order_quantity, String product_order_text, Users user, Stores stores, Products products) {
        this.product_order_quantity = product_order_quantity;
        this.product_order_text = product_order_text;
        this.user = user;
        this.stores = stores;
        this.products = products;
    }

    public static Product_order createOrder(int product_order_quantity, String product_order_text,
                                            Users user, Stores stores, Products products) {
        Product_order order = new Product_order();
        order.product_order_quantity = product_order_quantity;
        order.product_order_text = product_order_text;
        order.user = user;
        order.stores = stores;
        order.products = products;
        return order;
    }

}
