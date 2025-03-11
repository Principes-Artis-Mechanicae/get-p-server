package es.princip.getp.persistence.adapter.storage;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@Table(name = "file_log")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class FileLogJpaEntity {
    
    @Id
    @Column(name = "file_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "file_name")
    private String filename;

    @Column(name = "member_id")
    private Long memberId;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime uploadedAt;
}
