package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "product_order")
    private List<Product_orderItem> orderItems;

    @Builder
    public Product_order(int product_order_quantity, String product_order_text, Users user, Stores stores) {
        this.product_order_quantity = product_order_quantity;
        this.product_order_text = product_order_text;
        this.user = user;
        this.stores = stores;
    }

}
