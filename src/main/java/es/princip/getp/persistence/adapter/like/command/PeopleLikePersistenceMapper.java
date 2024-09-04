package es.princip.getp.persistence.adapter.like.command;

import org.mapstruct.Mapper;

import es.princip.getp.domain.like.model.people.PeopleLike;
import es.princip.getp.persistence.adapter.like.command.people.PeopleLikeJpaEntity;

@Mapper(componentModel = "spring")
public interface PeopleLikePersistenceMapper {
    PeopleLike mapToDomain(PeopleLikeJpaEntity peopleLikeJpaEntity);

    PeopleLikeJpaEntity mapToJpa(PeopleLike peopleLike);
}
