package es.princip.getp.domain.serviceTerm.domain.entity;

import java.time.LocalDateTime;
import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
