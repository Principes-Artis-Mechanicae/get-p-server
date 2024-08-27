package es.princip.getp.persistence.adapter.member;

import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MemberPersistenceMapper {

    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "password", target = "password.value")
    Member mapToDomain(MemberJpaEntity memberJpaEntity);

    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "password", source = "password.value")
    @Mapping(target = "nickname", source = "nickname.value")
    @Mapping(target = "phoneNumber", source = "phoneNumber.value")
    @Mapping(target = "profileImage", source = "profileImage.url")
    MemberJpaEntity mapToJpa(Member member);

    @Mapping(source = "tag", target = "tag.value")
    ServiceTermAgreement mapToDomain(ServiceTermAgreementJpaVO serviceTermAgreementJpaVO);

    @Mapping(target = "tag", source = "tag.value")
    ServiceTermAgreementJpaVO mapToJpa(ServiceTermAgreement serviceTermAgreement);

    @Mapping(source = "value", target = "value")
    PhoneNumber mapToPhoneNumber(String value);

    @Mapping(source = "value", target = "value")
    Nickname mapToNickname(String value);

    @Mapping(source = "value", target = "url")
    ProfileImage mapToProfileImage(String value);
}
