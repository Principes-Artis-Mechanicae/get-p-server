package es.princip.getp.domain.member.dto.response;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.domain.MemberType;
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
            .email(member.getEmail())
            .nickname(member.getNickname())
            .profileImageUri(member.getProfileImageUri())
            .memberType(member.getMemberType())
            .createdAt(member.getCreatedAt())
            .updatedAt(member.getUpdatedAt())
            .build();
    }
}
