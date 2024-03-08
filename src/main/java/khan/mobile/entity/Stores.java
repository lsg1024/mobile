package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE Stores SET DELETED = true where STORE_ID")
@SQLRestriction("deleted = false")
public class Stores extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long store_id;
    private String storeName;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "stores")
    private List<ProductOrder> productOrders;

    @Builder
    public Stores(Long store_id, String storeName, List<ProductOrder> productOrders) {
        this.store_id = store_id;
        this.storeName = storeName;
        this.productOrders = productOrders;
    }
}
