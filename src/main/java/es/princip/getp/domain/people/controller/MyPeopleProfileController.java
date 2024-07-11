package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.response.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.service.PeopleProfileService;
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
        @RequestBody @Valid CreatePeopleProfileRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        peopleProfileService.create(member.getMemberId(), request);
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
        DetailPeopleProfileResponse response = DetailPeopleProfileResponse.from(peopleProfileService.getByMemberId(member.getMemberId()));
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
            @RequestBody @Valid UpdatePeopleProfileRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        peopleProfileService.update(member.getMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }
}