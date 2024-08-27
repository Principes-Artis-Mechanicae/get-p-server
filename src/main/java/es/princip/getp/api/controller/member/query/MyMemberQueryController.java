package es.princip.getp.api.controller.member.query;

import es.princip.getp.api.controller.ApiResponse;
import es.princip.getp.api.controller.member.query.dto.response.MemberResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member/me")
@RequiredArgsConstructor
public class MyMemberQueryController {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse.ApiSuccessResult<MemberResponse>> getMyMember(
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Member member = principalDetails.getMember();
        return ApiResponse.success(HttpStatus.OK, MemberResponse.from(member));
    }
}
