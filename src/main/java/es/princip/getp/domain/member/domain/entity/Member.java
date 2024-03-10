package es.princip.getp.domain.member.domain.entity;

import es.princip.getp.domain.member.domain.enums.MemberType;
import java.util.ArrayList;
import java.util.List;
import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.serviceTerm.domain.entity.ServiceTermAgreement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueEmail", columnNames = {"email"})})
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    private MemberType memberType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ServiceTermAgreement> serviceTermAgreements = new ArrayList<>();

    @Builder
    public Member(String email, String password, MemberType memberType) {
        this.email = email;
        this.password = password;
        this.memberType = memberType;
    }
}
