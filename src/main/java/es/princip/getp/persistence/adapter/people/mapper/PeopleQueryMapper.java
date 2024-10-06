package es.princip.getp.persistence.adapter.people.mapper;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PortfolioResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.persistence.adapter.common.mapper.TechStackPersistenceMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.PortfolioJpaVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        PeoplePersistenceMapper.class,
        TechStackPersistenceMapper.class
    }
)
public abstract class PeopleQueryMapper {

    protected abstract PortfolioResponse mapToResponse(PortfolioJpaVO portfolio);

    public abstract CardPeopleProfileResponse mapToCardResponse(PeopleProfileJpaVO profile);

    @Mapping(source = "school", target = "education.school")
    @Mapping(source = "major", target = "education.major")
    public abstract DetailPeopleProfileResponse mapToDetailResponse(PeopleProfileJpaVO profile);

    @Deprecated
    public abstract PublicDetailPeopleProfileResponse mapToPublicDetailResponse(PeopleProfileJpaVO profile);
}
