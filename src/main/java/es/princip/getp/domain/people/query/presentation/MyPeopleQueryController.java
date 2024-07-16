package es.princip.getp.domain.people.query.presentation;

import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dto.people.PeopleResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.security.details.PrincipalDetails;
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

    private final PeopleDao peopleDao;

    /**
     * 내 피플 정보 조회
     *
     * @return 내 피플 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PeopleResponse>> getMyPeople(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getMemberId();
        PeopleResponse response = peopleDao.findByMemberId(memberId)
            .orElseThrow(
                () -> new BusinessLogicException(PeopleErrorCode.NOT_FOUND)
            );
        return ResponseEntity.ok()
            .body(ApiResponse.success(HttpStatus.OK, response));
    }
}