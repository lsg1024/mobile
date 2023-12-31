package khan.mobile.entity;

import jakarta.persistence.*;

@Entity
public class Product_order {

    @Id @GeneratedValue
    @Column(name = "product_order_id")
    private Long product_order_id;
    private int product_order_quantity;
    private String product_order_text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
