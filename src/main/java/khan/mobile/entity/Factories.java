package khan.mobile.entity;

import jakarta.persistence.*;
import khan.mobile.dto.FactoryDto;
import khan.mobile.entity.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE FACTORIES SET deleted = true where FACTORY_ID = ?")
@SQLRestriction("deleted = false")
public class Factories extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factoryId")
    private Long factoryId;
    private String factoryName;
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "factory")
    private List<Products> product;

    @Builder
    public Factories(Long factoryId, String factoryName) {
        this.factoryId = factoryId;
        this.factoryName = factoryName;
    }

    public void updateFactoryName(FactoryDto.Update factoryDto) {

        if (factoryDto.getFactoryName() != null) {
            this.factoryName = factoryDto.getFactoryName();
        }
    }
}
