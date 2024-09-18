package es.princip.getp.api.controller.like.command;

import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.like.people.port.in.LikePeopleUseCase;
import es.princip.getp.application.like.people.port.in.UnlikePeopleUseCase;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleLikeController {
    
    private final LikePeopleUseCase likePeopleUseCase;
    private final UnlikePeopleUseCase unlikePeopleUseCase;

    /**
     * 피플 좋아요
     *
     * @param peopleId 좋아요를 누를 피플 ID
     * @param principalDetails 로그인한 사용자 정보
     */
    @PostMapping("/{peopleId}/likes")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> like(
        @PathVariable final Long peopleId,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        likePeopleUseCase.like(memberId, peopleId);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    /**
     * 피플 좋아요 취소
     *
     * @param peopleId 좋아요를 취소할 피플 ID
     * @param principalDetails 로그인한 사용자 정보
     */
    @DeleteMapping("/{peopleId}/likes")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> unlike(
        @PathVariable final Long peopleId,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        unlikePeopleUseCase.unlike(memberId, peopleId);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
