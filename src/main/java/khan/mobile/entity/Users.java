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
    @Column(name = "user_id")
    private Long user_id;

    private String name;
    @Column(length = 45)
    private String email;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Products> products;

    @OneToMany(mappedBy = "user")
    private List<Product_order>  productOrders;

//    public void encodePassword(PasswordEncoder passwordEncoder) {
//        this.password = passwordEncoder.encode(password);
//    }

}
