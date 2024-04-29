package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Users extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(length = 45)
    private String email;

    @Column(length = 100)
    private String password;

    private String name;

    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Products> products;

    @OneToMany(mappedBy = "user")
    private List<ProductOrder>  productOrders;

    @Builder
    public Users(Long userId, String userEmail, String name, String username ,String userPassword, Role role) {
        this.userId = userId;
        this.email = userEmail;
        this.name = name;
        this.username = username;
        this.password = userPassword;
        this.role = role;
    }

    public void updateOAuth2(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
