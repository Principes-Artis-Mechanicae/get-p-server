package es.princip.getp.persistence.adapter.member;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.member.model.ProfileImage;
import es.princip.getp.domain.member.model.ServiceTermAgreement;
import es.princip.getp.persistence.adapter.common.mapper.PhoneNumberPersistenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PhoneNumberPersistenceMapper.class})
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
    Nickname mapToNickname(String value);

    @Mapping(source = "value", target = "url")
    ProfileImage mapToProfileImage(String value);
}
