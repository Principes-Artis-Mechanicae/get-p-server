package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.common.ControllerSupport;
import es.princip.getp.api.controller.common.dto.ApiResponse;
import es.princip.getp.api.controller.common.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.controller.common.dto.PageResponse;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.application.people.port.in.GetPeopleQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<? extends ApiSuccessResult<?>> getPeople(@PathVariable final Long peopleId) {
        if (isAuthenticated()) {
            final DetailPeopleResponse response = getPeopleQuery.getDetailById(peopleId);
            return ApiResponse.success(HttpStatus.OK, response);
        }
        final PublicDetailPeopleResponse response = getPeopleQuery.getPublicDetailById(peopleId);
        return ApiResponse.success(HttpStatus.OK, response);
    }

    /**
     * 피플 목록 조회
     *
     * @param pageable 정렬 기준
     * @return 정렬 기준에 해당되는 피플 정보 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<PageResponse<CardPeopleResponse>>> getCardPeoplePage(
        @PageableDefault(sort = "peopleId", direction = Sort.Direction.DESC) final Pageable pageable) {
        final PageResponse<CardPeopleResponse> response = PageResponse.from(getPeopleQuery.getPagedCards(pageable));
        return ApiResponse.success(HttpStatus.OK, response);
    }
}