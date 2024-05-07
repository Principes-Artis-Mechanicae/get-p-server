package es.princip.getp.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
            null,
            member.getMemberType(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }

    public static MemberResponse of(
        final Member member,
        final String nickname
    ) {
        return new MemberResponse(
            member.getMemberId(),
            member.getEmail(),
            nickname,
            member.getMemberType(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }
}
