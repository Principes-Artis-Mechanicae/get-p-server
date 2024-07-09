package es.princip.getp.domain.member.controller;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.dto.response.MemberResponse;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static es.princip.getp.global.util.ApiResponse.ApiSuccessResult;

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
        return ResponseEntity.created(memberService.updateProfileImage(member.getMemberId(), image))
            .body(ApiResponse.success(HttpStatus.CREATED));
    }
}
