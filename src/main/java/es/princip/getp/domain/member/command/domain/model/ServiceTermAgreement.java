package es.princip.getp.domain.member.command.domain.model;

import es.princip.getp.domain.serviceTerm.domain.ServiceTermTag;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceTermAgreement {

    @Column(name = "agreed")
    @NotNull
    private boolean agreed;
    
    @Column(name = "agreed_at")
    @NotNull
    private LocalDateTime agreedAt;

    @Embedded
    @NotNull
    private ServiceTermTag tag;

    public ServiceTermAgreement(final ServiceTermTag tag, final boolean agreed) {
        this.agreed = agreed;
        this.tag = tag;
        this.agreedAt = LocalDateTime.now();
    }
}
