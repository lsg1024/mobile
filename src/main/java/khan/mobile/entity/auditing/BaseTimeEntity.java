package khan.mobile.entity.auditing;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private String createDate;

    @LastModifiedDate
    private String lastModifiedDate;

//    @PrePersist
//    public void onPrePersist(){
//        this.createDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        this.lastModifiedDate = this.createDate;
//    }
//
//    @PreUpdate
//    public void onPreUpdate(){
//        this.lastModifiedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//    }


}
