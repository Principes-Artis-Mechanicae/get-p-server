package es.princip.getp.domain.member.dto.response;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;

import java.time.LocalDateTime;


public record MemberResponse(
    Long memberId,
    String email,
    String nickname,
    MemberType memberType,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static MemberResponse from(final Member member) {
        return new MemberResponse(
            member.getMemberId(),
            member.getEmail(),
            member.getNickname(),
            member.getMemberType(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }
}
