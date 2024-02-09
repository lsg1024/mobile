package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Users extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(length = 45)
    private String email;

    @Column(length = 100)
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Products> products;

    @OneToMany(mappedBy = "user")
    private List<ProductOrder>  productOrders;

}
