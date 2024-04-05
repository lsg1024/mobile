package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RefreshTokenId")
    private Long id;
    private String name;
    private String refreshToken;
    private String expiration;

    @Builder
    public RefreshEntity(String name, String refreshToken, String expiration) {
        this.name = name;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
