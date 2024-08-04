package es.princip.getp.domain.people.command.presentation;

import es.princip.getp.domain.common.infra.HashtagMapper;
import es.princip.getp.domain.common.infra.TechStackMapper;
import es.princip.getp.domain.common.infra.URLMapper;
import es.princip.getp.domain.people.command.application.command.EditPeopleProfileCommand;
import es.princip.getp.domain.people.command.application.command.RegisterPeopleProfileCommand;
import es.princip.getp.domain.people.command.domain.Portfolio;
import es.princip.getp.domain.people.command.presentation.dto.request.EditPeopleProfileRequest;
import es.princip.getp.domain.people.command.presentation.dto.request.PortfolioRequest;
import es.princip.getp.domain.people.command.presentation.dto.request.RegisterPeopleProfileRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {HashtagMapper.class, TechStackMapper.class, URLMapper.class})
public interface PeopleCommandMapper {

    RegisterPeopleProfileCommand mapToCommand(Long memberId, RegisterPeopleProfileRequest request);

    EditPeopleProfileCommand mapToCommand(Long memberId, EditPeopleProfileRequest request);

    Portfolio mapToPortfolio(PortfolioRequest request);
}
