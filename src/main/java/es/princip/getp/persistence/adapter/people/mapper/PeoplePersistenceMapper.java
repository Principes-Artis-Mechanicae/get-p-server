package es.princip.getp.persistence.adapter.people.mapper;

import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleProfile;
import es.princip.getp.domain.people.model.Portfolio;
import es.princip.getp.persistence.adapter.common.mapper.HashtagPersistenceMapper;
import es.princip.getp.persistence.adapter.common.mapper.TechStackPersistenceMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.PortfolioJpaVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {TechStackPersistenceMapper.class, HashtagPersistenceMapper.class}
)
public abstract class PeoplePersistenceMapper {

    @Mapping(source = "id", target = "id.value")
    @Mapping(source = "memberId", target = "memberId.value")
    @Mapping(source = "email", target = "info.email.value")
    public abstract People mapToDomain(PeopleJpaEntity people);

    @Mapping(source = "school", target = "education.school")
    @Mapping(source = "major", target = "education.major")
    protected abstract PeopleProfile mapToDomain(PeopleProfileJpaVO profile);

    @Mapping(source = "url", target = "url.value")
    protected abstract Portfolio mapToDomain(PortfolioJpaVO portfolio);

    @InheritInverseConfiguration
    public abstract PeopleJpaEntity mapToJpa(People people);

    @InheritInverseConfiguration
    protected abstract PeopleProfileJpaVO mapToJpa(PeopleProfile profile);

    @InheritInverseConfiguration
    protected abstract PortfolioJpaVO mapToJpa(Portfolio portfolio);
}
