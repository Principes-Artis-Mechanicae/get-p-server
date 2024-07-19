package es.princip.getp.domain.member.command.presentation;

import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.presentation.dto.response.ProfileImageResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.security.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/member/me")
@RequiredArgsConstructor
public class MyMemberController {

    private final MemberService memberService;

    @PostMapping("/profile-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> uploadProfileImage(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @RequestPart MultipartFile image
    ) {
        Long memberId = principalDetails.getMember().getMemberId();
        String profileImageUri = memberService.changeProfileImage(memberId, image);
        ProfileImageResponse response = new ProfileImageResponse(profileImageUri);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, response));
    }
}
