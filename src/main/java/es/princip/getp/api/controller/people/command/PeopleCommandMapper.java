package es.princip.getp.api.controller.people.command;

import es.princip.getp.api.controller.common.mapper.CommandMapper;
import es.princip.getp.api.controller.common.mapper.HashtagMapper;
import es.princip.getp.api.controller.common.mapper.TechStackMapper;
import es.princip.getp.api.controller.common.mapper.URLMapper;
import es.princip.getp.api.controller.people.command.dto.request.EditPeopleProfileRequest;
import es.princip.getp.api.controller.people.command.dto.request.PortfolioRequest;
import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleProfileRequest;
import es.princip.getp.application.people.command.EditPeopleProfileCommand;
import es.princip.getp.application.people.command.RegisterPeopleProfileCommand;
import es.princip.getp.domain.people.model.Portfolio;
import org.mapstruct.Mapper;

@CommandMapper
@Mapper(componentModel = "spring", uses = {HashtagMapper.class, TechStackMapper.class, URLMapper.class})
public interface PeopleCommandMapper {

    RegisterPeopleProfileCommand mapToCommand(Long memberId, RegisterPeopleProfileRequest request);

    EditPeopleProfileCommand mapToCommand(Long memberId, EditPeopleProfileRequest request);

    Portfolio mapToPortfolio(PortfolioRequest request);
}
