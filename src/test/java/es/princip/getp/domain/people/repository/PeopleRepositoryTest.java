package es.princip.getp.domain.people.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.entity.MemberType;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.PeopleRoleType;


@DataJpaTest
public class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    @DisplayName("People이 DB에 저장 되는지 확인")
    void testSave() {
        Member member = new Member("hello@example.com", "hello", MemberType.ROLE_PEOPLE);
        People people = People.builder()
                                .name("홍길동")
                                .email("hello@example.com")
                                .phoneNumber("010-1234-5678")
                                .roleType(PeopleRoleType.valueOf("ROLE_INDIVIDUAL"))
                                .profileImageUri("https://he.princip.es/img")
                                .accountNumber("3332-112-12-12")
                                .member(member)
                            .build();
        peopleRepository.save(people);
    }

    // @Test
    // @DisplayName("MemberId로 People 객체 조회하기")
    // void testFindByMember_MemberId() {
    // }
}
