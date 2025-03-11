package es.princip.getp.persistence.adapter.people.mapper;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PortfolioResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.persistence.adapter.common.mapper.HashtagPersistenceMapper;
import es.princip.getp.persistence.adapter.common.mapper.TechStackPersistenceMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.PortfolioJpaVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        PeoplePersistenceMapper.class,
        TechStackPersistenceMapper.class,
        HashtagPersistenceMapper.class
    }
)
public abstract class PeopleQueryMapper {

    protected abstract PortfolioResponse mapToResponse(PortfolioJpaVO portfolio);

    public abstract CardPeopleProfileResponse mapToCardPeopleProfileResponse(PeopleProfileJpaVO profile);

    @Mapping(source = "school", target = "education.school")
    @Mapping(source = "major", target = "education.major")
    public abstract DetailPeopleProfileResponse mapToDetailPeopleProfileResponse(PeopleProfileJpaVO profile);

    public abstract PublicDetailPeopleProfileResponse mapToPublicPeopleProfileResponse(PeopleProfileJpaVO profile);
}
