package es.princip.getp.domain.member.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.member.dto.request.CreateMemberRequest;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.serviceTerm.domain.entity.ServiceTermAgreement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    @Setter
    private Client client;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    @Setter
    private People people;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ServiceTermAgreement> serviceTermAgreements = new ArrayList<>();

    @Builder
    public Member(
        Long memberId,
        String email,
        String password,
        MemberType memberType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.memberType = memberType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getNickname() {
        if (client != null)
            return client.getNickname();
        if (people != null)
            return people.getNickname();
        return null;
    }

    public static Member from(CreateMemberRequest request) {
        return Member.builder()
            .email(request.email())
            .password(request.password())
            .memberType(request.memberType())
            .build();
    }
}
