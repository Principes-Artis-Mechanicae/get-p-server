package es.princip.getp.domain.member.presentation.dto.response;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.MemberType;

import java.time.LocalDateTime;

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
            member.getMemberId(),
            member.getEmail().getValue(),
            member.getNickname().getValue(),
            member.getProfileImage().getUri(),
            member.getMemberType(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }
}
