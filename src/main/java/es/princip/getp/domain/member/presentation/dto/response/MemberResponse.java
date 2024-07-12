package es.princip.getp.domain.member.presentation.dto.response;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.MemberType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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
        return MemberResponse.builder()
            .memberId(member.getMemberId())
            .email(member.getEmail().getValue())
            .nickname(member.getNickname().getValue())
            .profileImageUri(member.getProfileImage().getUri().toString())
            .memberType(member.getMemberType())
            .createdAt(member.getCreatedAt())
            .updatedAt(member.getUpdatedAt())
            .build();
    }
}
