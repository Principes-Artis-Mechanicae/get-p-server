package es.princip.getp.persistence.adapter.serviceTerm;

import es.princip.getp.domain.serviceTerm.model.ServiceTerm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ServiceTermPersistenceMapper {

    @Mapping(source = "tag", target = "tag.value")
    ServiceTerm mapToDomain(ServiceTermJpaEntity serviceTermJpaEntity);

    @Mapping(target = "tag", source = "tag.value")
    ServiceTermJpaEntity mapToJpa(ServiceTerm serviceTerm);
}
