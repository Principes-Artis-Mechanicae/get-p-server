package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.domain.like.people.model.PeopleLike;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PeopleLikePersistenceMapper {

    @Mapping(source = "peopleId", target = "peopleId.value")
    PeopleLike mapToDomain(PeopleLikeJpaEntity peopleLikeJpaEntity);

    @InheritInverseConfiguration
    PeopleLikeJpaEntity mapToJpa(PeopleLike peopleLike);
}
