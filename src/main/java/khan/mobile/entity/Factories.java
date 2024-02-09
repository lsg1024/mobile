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
    @Column(name = "factoryId")
    private Long factoryId;
    private String factoryName;

    @OneToMany(mappedBy = "factory")
    private List<Products> product;

    @Builder
    public Factories(Long factoryId, String factoryName) {
        this.factoryId = factoryId;
        this.factoryName = factoryName;
    }
}
