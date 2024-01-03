package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Stores {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long store_id;
    private String store_name;

    @OneToMany(mappedBy = "stores")
    private List<Product_order> productOrders;

    @Builder
    public Stores(String store_name, List<Product_order> productOrders) {
        this.store_name = store_name;
        this.productOrders = productOrders;
    }
}
