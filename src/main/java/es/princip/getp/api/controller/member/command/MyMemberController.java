package es.princip.getp.api.controller.member.command;

import es.princip.getp.api.controller.ApiResponse;
import es.princip.getp.api.controller.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.controller.member.command.dto.response.ProfileImageResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.application.member.command.ChangeProfileImageCommand;
import es.princip.getp.application.member.port.in.ChangeProfileImageUseCase;
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
@RequiredArgsConstructor
@RequestMapping("/member/me")
public class MyMemberController {

    private final ChangeProfileImageUseCase changeProfileImageUseCase;

    /**
     * 내 프로필 이미지 등록
     *
     * @param principalDetails 로그인한 사용자 정보
     * @param image 프로필 이미지
     * @return 등록된 프로필 이미지의 URI
     */
    @PostMapping("/profile-image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ProfileImageResponse>> uploadProfileImage(
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @RequestPart final MultipartFile image
    ) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final ChangeProfileImageCommand command = new ChangeProfileImageCommand(memberId, image);
        final String profileImageUri = changeProfileImageUseCase.changeProfileImage(command);
        final ProfileImageResponse response = new ProfileImageResponse(profileImageUri);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
