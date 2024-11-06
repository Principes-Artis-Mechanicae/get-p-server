package es.princip.getp.api.controller.member.command;

import es.princip.getp.api.controller.member.command.dto.response.ProfileImageResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.member.command.RegisterProfileImageCommand;
import es.princip.getp.application.member.port.in.ProfileImageUseCase;
import es.princip.getp.domain.member.model.MemberId;
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

    private final ProfileImageUseCase profileImageUseCase;

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
        final MemberId memberId = principalDetails.getMember().getId();
        final RegisterProfileImageCommand command = new RegisterProfileImageCommand(memberId, image);
        final String profileImageUri = profileImageUseCase.registerProfileImage(command);
        final ProfileImageResponse response = new ProfileImageResponse(profileImageUri);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }
}
