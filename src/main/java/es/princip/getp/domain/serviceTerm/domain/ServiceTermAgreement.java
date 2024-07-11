package es.princip.getp.domain.serviceTerm.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_term_agreement")
public class ServiceTermAgreement extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_term_agreement_id")
    private Long serviceTermAgreementId;

    @Column(name = "agreed")
    private boolean agreed;
    
    @Column(name = "agreed_at")
    private LocalDateTime agreedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "service_term_id")
    private ServiceTerm serviceTerm;

    @Builder
    public ServiceTermAgreement(boolean agreed, ServiceTerm serviceTerm, Member member) {
        this.agreed = agreed;
        this.serviceTerm = serviceTerm;
        this.member = member;
        this.agreedAt = LocalDateTime.now();
    }
}
