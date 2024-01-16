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
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Products> products;

    @OneToMany(mappedBy = "user")
    private List<Product_order>  productOrders;

    @Builder
    public Users(Long user_id, String id, String password, String email, Role role) {
        this.user_id = user_id;
        this.id = id;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
