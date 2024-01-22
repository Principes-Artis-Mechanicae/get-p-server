package es.princip.getp.domain.people.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private PeopleRoleType roleType;

    @Column(name = "profile_image_uri")
    private String profileImageUri;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Member member;

    @Builder
    public People(String name, String email, String phoneNumber, PeopleRoleType roleType, 
            String profileImageUri, String accountNumber, Member member) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleType = roleType;
        this.profileImageUri = profileImageUri;
        this.accountNumber = accountNumber;
        this.member = member;
    }

    public void update(UpdatePeopleRequest request) {
        this.name = request.name();
        this.email = request.email();
        this.phoneNumber = request.phoneNumber();
        this.roleType = PeopleRoleType.valueOf(request.roleType());
        this.profileImageUri = request.profileImageUri();
        this.accountNumber = request.accountNumber();
    }
}
