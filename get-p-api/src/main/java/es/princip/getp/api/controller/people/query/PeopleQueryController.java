package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.support.ControllerSupport;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.application.people.dto.command.GetPeopleCommand;
import es.princip.getp.application.people.dto.command.PeopleSearchFilter;
import es.princip.getp.application.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.application.people.dto.response.people.PeopleDetailResponse;
import es.princip.getp.application.people.port.in.GetPeopleQuery;
import es.princip.getp.application.support.dto.PageResponse;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.people.model.PeopleId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleQueryController extends ControllerSupport {

    private final GetPeopleQuery getPeopleQuery;

    /**
     * 피플 상세 조회
     * 
     * @param peopleId 피플 ID
     * @return 피플 ID에 해당되는 피플 상세 정보
     */
    @GetMapping("/{peopleId}")
    public ResponseEntity<ApiSuccessResult<PeopleDetailResponse>> getPeople(
        @AuthenticationPrincipal final PrincipalDetails principalDetails,
        @PathVariable final Long peopleId
    ) {
        final PeopleId pid = new PeopleId(peopleId);
        final Member member = Optional.ofNullable(principalDetails)
            .map(PrincipalDetails::getMember)
            .orElse(null);
        final PeopleDetailResponse response = getPeopleQuery.getDetailBy(member, pid);
        return ApiResponse.success(HttpStatus.OK, response);
    }

    /**
     * 피플 목록 조회
     *
     * @param pageable 정렬 기준
     * @return 정렬 기준에 해당되는 피플 정보 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<PageResponse<CardPeopleResponse>>> getPeopleList(
        @PageableDefault(sort = "peopleId", direction = Sort.Direction.DESC) final Pageable pageable,
        @ModelAttribute final PeopleSearchFilter filter,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final Member member = Optional.ofNullable(principalDetails)
            .map(PrincipalDetails::getMember)
            .orElse(null);
        final GetPeopleCommand command = new GetPeopleCommand(pageable, filter, member);
        final PageResponse<CardPeopleResponse> response = PageResponse.from(getPeopleQuery.getPagedCards(command));
        return ApiResponse.success(HttpStatus.OK, response);
    }
}