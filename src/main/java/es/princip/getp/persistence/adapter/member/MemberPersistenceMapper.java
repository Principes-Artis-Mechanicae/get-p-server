package es.princip.getp.persistence.adapter.member;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.ServiceTermAgreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberPersistenceMapper {

    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "password", target = "password.value")
    @Mapping(source = "nickname", target = "nickname.value")
    @Mapping(source = "phoneNumber", target = "phoneNumber.value")
    @Mapping(source = "profileImageUrl", target = "profileImage.url")
    Member mapToDomain(MemberJpaEntity memberJpaEntity);

    @Mapping(target = "email", source = "email.value")
    @Mapping(target = "password", source = "password.value")
    @Mapping(target = "nickname", source = "nickname.value")
    @Mapping(target = "phoneNumber", source = "phoneNumber.value")
    @Mapping(target = "profileImageUrl", source = "profileImage.url")
    MemberJpaEntity mapToJpa(Member member);

    @Mapping(source = "tag", target = "tag.value")
    ServiceTermAgreement mapToDomain(ServiceTermAgreementJpaVO serviceTermAgreementJpaVO);

    @Mapping(target = "tag", source = "tag.value")
    ServiceTermAgreementJpaVO mapToJpa(ServiceTermAgreement serviceTermAgreement);
}
