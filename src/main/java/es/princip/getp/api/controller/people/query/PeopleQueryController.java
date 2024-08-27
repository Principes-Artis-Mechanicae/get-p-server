package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.ApiResponse;
import es.princip.getp.api.controller.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.controller.ControllerSupport;
import es.princip.getp.api.controller.PageResponse;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.query.dao.PeopleDao;
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

    private final PeopleDao peopleDao;

    /**
     * 피플 상세 조회
     * 
     * @param peopleId 피플 ID
     * @return 피플 ID에 해당되는 피플 상세 정보
     */
    @GetMapping("/{peopleId}")
    public ResponseEntity<? extends ApiSuccessResult<?>> getPeople(@PathVariable final Long peopleId) {
        if (isAuthenticated()) {
            final DetailPeopleResponse response = peopleDao.findDetailPeopleById(peopleId);
            return ApiResponse.success(HttpStatus.OK, response);
        }
        final PublicDetailPeopleResponse response = peopleDao.findPublicDetailPeopleById(peopleId);
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
        final PageResponse<CardPeopleResponse> response = PageResponse.from(peopleDao.findCardPeoplePage(pageable));
        return ApiResponse.success(HttpStatus.OK, response);
    }
}