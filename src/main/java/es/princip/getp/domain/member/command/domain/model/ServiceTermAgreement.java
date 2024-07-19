package es.princip.getp.domain.member.command.domain.model;

import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceTermAgreement {

    @Column(name = "agreed")
    private boolean agreed;
    
    @Column(name = "agreed_at")
    private LocalDateTime agreedAt;

    @Embedded
    private ServiceTermTag tag;

    public ServiceTermAgreement(ServiceTermTag tag, boolean agreed) {
        this.agreed = agreed;
        this.tag = tag;
        this.agreedAt = LocalDateTime.now();
    }
}
