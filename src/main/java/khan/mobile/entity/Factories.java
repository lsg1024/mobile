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
public class Factories {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factory_id")
    private Long factory_id;
    private String factory_name;

    @OneToMany(mappedBy = "factory")
    private List<Products> product;

    @Builder
    public Factories(Long factory_id, String factory_name) {
        this.factory_id = factory_id;
        this.factory_name = factory_name;
    }
}
