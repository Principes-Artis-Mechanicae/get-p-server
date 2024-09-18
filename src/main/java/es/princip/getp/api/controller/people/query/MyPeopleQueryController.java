package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.people.query.dto.people.MyPeopleResponse;
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
@RequestMapping("/people/me")
@RequiredArgsConstructor
public class MyPeopleQueryController {

    private final GetMyPeopleQuery getMyPeopleQuery;

    /**
     * 내 피플 정보 조회
     *
     * @return 내 피플 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<MyPeopleResponse>> getMyPeople(
        @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final MemberId memberId = principalDetails.getMember().getId();
        final MyPeopleResponse response = getMyPeopleQuery.getBy(memberId);
        return ApiResponse.success(HttpStatus.OK, response);
    }
}