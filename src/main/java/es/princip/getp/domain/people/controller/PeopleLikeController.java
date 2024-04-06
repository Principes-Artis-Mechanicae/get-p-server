package es.princip.getp.domain.people.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.princip.getp.domain.people.service.PeopleLikeService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import lombok.RequiredArgsConstructor;

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
}
