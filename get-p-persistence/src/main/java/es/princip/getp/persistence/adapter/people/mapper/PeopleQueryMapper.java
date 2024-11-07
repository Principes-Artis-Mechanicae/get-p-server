package es.princip.getp.persistence.adapter.people.mapper;

import es.princip.getp.application.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PortfolioResponse;
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
    public abstract PeopleProfileDetailResponse mapToDetailResponse(PeopleProfileJpaVO profile);

}
