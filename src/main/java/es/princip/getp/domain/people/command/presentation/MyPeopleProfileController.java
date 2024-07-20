package es.princip.getp.domain.people.command.presentation;

import es.princip.getp.domain.people.command.application.PeopleProfileService;
import es.princip.getp.domain.people.command.presentation.dto.request.EditPeopleProfileRequest;
import es.princip.getp.domain.people.command.presentation.dto.request.WritePeopleProfileRequest;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people/me/profile")
@RequiredArgsConstructor
public class MyPeopleProfileController {

    private final PeopleProfileService peopleProfileService;

    /**
     * 내 피플 프로필 등록
     *
     * @param request 등록할 피플 프로필 정보
     * @return 등록된 피플 프로필 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> createMyPeopleProfile(
        @RequestBody @Valid final WritePeopleProfileRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Long memberId = principalDetails.getMember().getMemberId();
        peopleProfileService.writeProfile(memberId, request);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    /**
     * 내 피플 프로필 수정
     * 
     * @param request 수정할 피플 프로필 정보
     * @return 수정된 피플 프로필 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> updateMyPeopleProfile(
            @RequestBody @Valid final EditPeopleProfileRequest request,
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Long memberId = principalDetails.getMember().getMemberId();
        peopleProfileService.editProfile(memberId, request);
        return ApiResponse.success(HttpStatus.CREATED);
    }
}