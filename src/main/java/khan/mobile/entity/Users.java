package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Users {

    @GeneratedValue @Id
    @Column(name = "user_id")
    private Long user_id;
    private String id;
    private String password;
    private Boolean role;

    @OneToMany
    @JoinColumn(name = "user")
    private List<Product_order>  orders;


}
