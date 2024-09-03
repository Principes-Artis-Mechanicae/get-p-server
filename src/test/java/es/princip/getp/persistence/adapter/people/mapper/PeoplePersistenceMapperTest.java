package es.princip.getp.persistence.adapter.people.mapper;

import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.persistence.adapter.PersistenceMapperTest;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static es.princip.getp.fixture.people.PeopleFixture.people;
import static es.princip.getp.fixture.people.PeopleFixture.peopleWithoutProfile;
import static es.princip.getp.persistence.adapter.people.PeoplePersistenceFixture.peopleJpaEntity;
import static es.princip.getp.persistence.adapter.people.PeoplePersistenceFixture.peopleJpaEntityWithoutProfile;
import static org.assertj.core.api.Assertions.assertThat;

class PeoplePersistenceMapperTest extends PersistenceMapperTest {

    @Autowired PeoplePersistenceMapper mapper;

    @Test
    void 도메인_모델로_매핑한다() {
        final PeopleJpaEntity peopleJpaEntity = peopleJpaEntity(1L, PeopleType.INDIVIDUAL);
        final People expected = people(1L, PeopleType.INDIVIDUAL);

        final People people = mapper.mapToDomain(peopleJpaEntity);

        assertThat(people).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @Test
    void 도메인_모델로_매핑_시_피플_프로필을_등록하지_않으면_NULL로_매핑한다() {
        final PeopleJpaEntity peopleJpaEntity = peopleJpaEntityWithoutProfile(1L, PeopleType.INDIVIDUAL);
        final People expected = peopleWithoutProfile(1L, PeopleType.INDIVIDUAL);

        final People people = mapper.mapToDomain(peopleJpaEntity);

        assertThat(people).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
        assertThat(people.getProfile()).isNull();
    }

    @Test
    void JPA_모델로_매핑한다() {
        final People people = people(1L, PeopleType.INDIVIDUAL);
        final PeopleJpaEntity expected = peopleJpaEntity(1L, PeopleType.INDIVIDUAL);

        final PeopleJpaEntity peopleJpaEntity = mapper.mapToJpa(people);

        assertThat(peopleJpaEntity).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }
}