package es.princip.getp.api.controller.auth;

import es.princip.getp.api.controller.CommandMapper;
import es.princip.getp.api.controller.auth.dto.request.ServiceTermAgreementRequest;
import es.princip.getp.api.controller.auth.dto.request.SignUpRequest;
import es.princip.getp.application.auth.command.SignUpCommand;
import es.princip.getp.domain.member.model.ServiceTermAgreementData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@CommandMapper
@Mapper(componentModel = "spring")
interface SignUpCommandMapper {

    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "password", target = "password.value")
    SignUpCommand mapToCommand(SignUpRequest request);

    @Mapping(source = "tag", target = "tag.value")
    ServiceTermAgreementData mapToCommand(ServiceTermAgreementRequest request);
}
