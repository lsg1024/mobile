package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;
    private String id;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Product_order>  productOrders;

    @Builder
    public Users(Long user_id, String id, String password, Role role, List<Product_order> productOrders) {
        this.user_id = user_id;
        this.id = id;
        this.password = password;
        this.role = role;
        this.productOrders = productOrders;
    }
}
