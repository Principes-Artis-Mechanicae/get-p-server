package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.domain.like.people.model.PeopleLike;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeopleLikePersistenceMapper {
    PeopleLike mapToDomain(PeopleLikeJpaEntity peopleLikeJpaEntity);

    PeopleLikeJpaEntity mapToJpa(PeopleLike peopleLike);
}
