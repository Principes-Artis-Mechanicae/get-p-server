package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.PeopleResponse;
import es.princip.getp.domain.people.dto.response.people.UpdatePeopleResponse;
import es.princip.getp.domain.people.dto.response.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.dto.response.peopleProfile.UpdatePeopleProfileResponse;
import es.princip.getp.domain.people.service.PeopleProfileService;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people/me")
@RequiredArgsConstructor
public class MyPeopleController {

    private final PeopleService peopleService;

    private final PeopleProfileService peopleProfileService;

    /**
     * 내 피플 정보 수정
     * 
     * @param request 수정할 피플 정보
     * @return 수정 완료된 피플 정보
     */
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<UpdatePeopleResponse>> updatePeople(
            @RequestBody @Valid UpdatePeopleRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        UpdatePeopleResponse response = UpdatePeopleResponse.from(peopleService.update(member.getMemberId(), request));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 피플 정보 조회
     * 
     * @return 내 피플 정보
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PeopleResponse>> getMyPeople(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        PeopleResponse response = PeopleResponse.from(peopleService.getByMemberId(member.getMemberId()));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 피플 프로필 조회
     * 
     * @return 내 피플 프로필 정보
     */
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<DetailPeopleProfileResponse>> getMyPeopleProfile(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        DetailPeopleProfileResponse response = DetailPeopleProfileResponse.from(peopleProfileService.getByMemberId(member.getMemberId()));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 피플 프로필 수정
     * 
     * @param request 수정할 피플 프로필 정보
     * @return 수정 완료된 피플 프로필 정보
     */
    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<UpdatePeopleProfileResponse>> updatePeopleProfile(
            @RequestBody @Valid UpdatePeopleProfileRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        UpdatePeopleProfileResponse response = UpdatePeopleProfileResponse.from(peopleProfileService.update(member.getMemberId(), request));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}