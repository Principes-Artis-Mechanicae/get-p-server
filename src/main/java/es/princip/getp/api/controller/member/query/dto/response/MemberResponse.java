package es.princip.getp.api.controller.member.query.dto.response;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.member.model.Nickname;
import es.princip.getp.domain.member.model.ProfileImage;

import java.time.LocalDateTime;
import java.util.Optional;

public record MemberResponse(
    Long memberId,
    String email,
    String nickname,
    String profileImageUri,
    MemberType memberType,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static MemberResponse from(final Member member) {
        return new MemberResponse(
            member.getMemberId().getValue(),
            member.getEmail().getValue(),
            Optional.ofNullable(member.getNickname())
                .map(Nickname::getValue)
                .orElse(null),
            Optional.ofNullable(member.getProfileImage())
                .map(ProfileImage::getUrl)
                .orElse(null),
            member.getMemberType(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }
}