package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.people.port.in.GetMyPeopleQuery;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people/me/profile")
@RequiredArgsConstructor
public class MyPeopleProfileQueryController {

    private final GetMyPeopleQuery getMyPeopleQuery;

    /**
     * 내 피플 프로필 조회
     * 
     * @return 내 피플 프로필 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PeopleProfileDetailResponse>> getMyPeopleProfile(
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final MemberId memberId = principalDetails.getMember().getId();
        final PeopleProfileDetailResponse response = getMyPeopleQuery.getDetailProfileBy(memberId);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}