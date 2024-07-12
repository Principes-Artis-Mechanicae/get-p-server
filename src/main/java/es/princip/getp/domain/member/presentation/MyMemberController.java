package es.princip.getp.domain.member.presentation;

import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.presentation.dto.response.MemberResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;

@RestController
@RequestMapping("/member/me")
@RequiredArgsConstructor
public class MyMemberController {

    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<MemberResponse>> getMyMember(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, MemberResponse.from(member)));
    }

    @PostMapping("/profile-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadProfileImage(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestPart MultipartFile image
    ) {
        Member member = principalDetails.getMember();
        return ResponseEntity.created(memberService.changeProfileImage(member.getMemberId(), image))
            .body(ApiResponse.success(HttpStatus.CREATED));
    }
}
