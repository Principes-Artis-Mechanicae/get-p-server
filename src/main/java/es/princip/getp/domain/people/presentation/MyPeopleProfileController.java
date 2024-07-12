package es.princip.getp.domain.people.presentation;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.people.application.PeopleProfileService;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleRepository;
import es.princip.getp.domain.people.presentation.dto.request.EditPeopleProfileRequest;
import es.princip.getp.domain.people.presentation.dto.request.WritePeopleProfileRequest;
import es.princip.getp.domain.people.presentation.dto.response.peopleProfile.DetailPeopleProfileResponse;
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
    private final PeopleRepository peopleRepository;

    /**
     * 내 피플 프로필 등록
     *
     * @param request 등록할 피플 프로필 정보
     * @return 등록된 피플 프로필 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> createMyPeopleProfile(
        @RequestBody @Valid WritePeopleProfileRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        peopleProfileService.writeProfile(member.getMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }

    /**
     * 내 피플 프로필 조회
     * 
     * @return 내 피플 프로필 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<DetailPeopleProfileResponse>> getMyPeopleProfile(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        People people = peopleRepository.findByMemberId(member.getMemberId()).orElseThrow();
        DetailPeopleProfileResponse response = DetailPeopleProfileResponse.from(people.getProfile());
        return ResponseEntity.ok()
            .body(ApiResponse.success(HttpStatus.OK, response));
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
            @RequestBody @Valid EditPeopleProfileRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        peopleProfileService.editProfile(member.getMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }
}