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
    private String storeName;

    @OneToMany(mappedBy = "stores")
    private List<Product_order> productOrders;

    @Builder
    public Stores(Long store_id, String storeName, List<Product_order> productOrders) {
        this.store_id = store_id;
        this.storeName = storeName;
        this.productOrders = productOrders;
    }
}
