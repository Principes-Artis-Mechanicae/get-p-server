package es.princip.getp.api.controller.common.mapper;

import es.princip.getp.domain.common.model.PhoneNumber;
import org.mapstruct.Mapper;

@CommandMapper
@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    PhoneNumber mapToPhoneNumber(String value);
}
