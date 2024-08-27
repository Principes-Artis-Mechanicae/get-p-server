package es.princip.getp.persistence.adapter.common.mapper;

import es.princip.getp.domain.common.model.PhoneNumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneNumberPersistenceMapper {

    PhoneNumber mapToPhoneNumber(String value);
}
