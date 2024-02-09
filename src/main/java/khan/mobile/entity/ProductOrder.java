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
public class ProductOrder extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productOrderId")
    private Long productOrderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Stores stores;

    @OneToMany(mappedBy = "productOrder")
    private List<ProductOrderItem> orderItems;

    @Builder
    public ProductOrder(Users user, Stores stores) {
        this.user = user;
        this.stores = stores;
    }

}
