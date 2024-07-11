package es.princip.getp.domain.people.presentation;

import es.princip.getp.domain.people.application.PeopleLikeService;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
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
    
    private final PeopleLikeService peopleLikeService;

    /**
     * 피플 좋아요
     *
     * @param peopleId 좋아요를 누를 피플 ID
     * @param principalDetails 로그인한 사용자 정보
     */
    @PostMapping("/{peopleId}/likes")
    @PreAuthorize("hasRole('CLIENT') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> like(
        @PathVariable Long peopleId,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        peopleLikeService.like(principalDetails.getMember().getMemberId(), peopleId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
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
        @PathVariable Long peopleId,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        peopleLikeService.unlike(principalDetails.getMember().getMemberId(), peopleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.success(HttpStatus.NO_CONTENT));
    }
}
