package es.princip.getp.common.infra;

import es.princip.getp.domain.member.command.domain.model.PhoneNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    PhoneNumber mapToPhoneNumber(String value);
}
