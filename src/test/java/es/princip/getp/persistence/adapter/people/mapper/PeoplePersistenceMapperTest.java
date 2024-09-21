package es.princip.getp.persistence.adapter.people.mapper;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.support.PersistenceMapperTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static es.princip.getp.fixture.people.PeopleFixture.people;
import static es.princip.getp.fixture.people.PeopleFixture.peopleWithoutProfile;
import static es.princip.getp.persistence.adapter.people.PeoplePersistenceFixture.peopleJpaEntity;
import static es.princip.getp.persistence.adapter.people.PeoplePersistenceFixture.peopleJpaEntityWithoutProfile;
import static org.assertj.core.api.Assertions.assertThat;

class PeoplePersistenceMapperTest extends PersistenceMapperTest {

    @Autowired PeoplePersistenceMapper mapper;

    private final MemberId memberId = new MemberId(1L);

    @Test
    void 도메인_모델로_매핑한다() {
        final PeopleJpaEntity peopleJpaEntity = peopleJpaEntity(memberId.getValue());
        final People expected = people(memberId);

        final People people = mapper.mapToDomain(peopleJpaEntity);

        assertThat(people).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @Test
    void 도메인_모델로_매핑_시_피플_프로필을_등록하지_않으면_NULL로_매핑한다() {
        final PeopleJpaEntity peopleJpaEntity = peopleJpaEntityWithoutProfile(memberId.getValue());
        final People expected = peopleWithoutProfile(memberId);

        final People people = mapper.mapToDomain(peopleJpaEntity);

        assertThat(people).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
        assertThat(people.getProfile()).isNull();
    }

    @Test
    void JPA_모델로_매핑한다() {
        final People people = people(memberId);
        final PeopleJpaEntity expected = peopleJpaEntity(memberId.getValue());

        final PeopleJpaEntity peopleJpaEntity = mapper.mapToJpa(people);

        assertThat(peopleJpaEntity).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }
}