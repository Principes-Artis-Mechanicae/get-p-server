package es.princip.getp.domain.people.presentation;

import es.princip.getp.domain.people.application.PeopleService;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.dto.response.people.DetailPeopleResponse;
import es.princip.getp.domain.people.dto.response.people.PublicDetailPeopleResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.dto.response.PageResponse;
import es.princip.getp.infra.support.ControllerSupport;
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
public class PeopleController extends ControllerSupport {

    private final PeopleService peopleService;

    /**
     * 포트폴리오 개발 진행 후 완성 예정 - 피플 상세 조회
     * 
     * @param peopleId 피플 ID
     * @return 피플 ID에 해당되는 피플 상세 정보
     */
    @GetMapping("/{peopleId}")
    public ResponseEntity<ApiSuccessResult<?>> getPeople(@PathVariable Long peopleId) {
        if (isAuthenticated()) {
            DetailPeopleResponse response = DetailPeopleResponse.from(peopleService.getByPeopleId(peopleId));
            return ResponseEntity.ok()
                .body(ApiResponse.success(HttpStatus.OK, response));
        }
        PublicDetailPeopleResponse response = PublicDetailPeopleResponse.from(peopleService.getByPeopleId(peopleId));
        return ResponseEntity.ok()
            .body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 피플 목록 조회
     *
     * @param pageable 정렬 기준
     * @return 정렬 기준에 해당되는 피플 정보 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<PageResponse<CardPeopleResponse>>> getCardPeoplePage(
        @PageableDefault(sort = "peopleId", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<CardPeopleResponse> response = PageResponse.from(peopleService.getCardPeoplePage(pageable));
        return ResponseEntity.ok()
            .body(ApiResponse.success(HttpStatus.OK, response));
    }
}