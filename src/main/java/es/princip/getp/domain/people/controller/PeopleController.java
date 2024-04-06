package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.dto.response.people.CreatePeopleResponse;
import es.princip.getp.domain.people.dto.response.people.DetailPeopleResponse;
import es.princip.getp.domain.people.dto.response.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleService peopleService;

    /**
     * 피플 정보 등록
     *
     * @param request 등록할 피플 정보
     * @return 등록된 피플 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CreatePeopleResponse>> createPeople(
        @RequestBody @Valid CreatePeopleRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        CreatePeopleResponse response = CreatePeopleResponse.from(peopleService.create(member, request));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, response));
    }

    /**
     * 포트폴리오 개발 진행 후 완성 예정 - 피플 상세 조회
     * 
     * @param peopleId 피플 ID
     * @return 피플 ID에 해당되는 피플 상세 정보
     */
    @GetMapping("/{peopleId}")
    public ResponseEntity<ApiSuccessResult<?>> getPeople(@PathVariable Long peopleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.isAuthenticated());
        if (authentication.isAuthenticated()) {
            DetailPeopleResponse response = DetailPeopleResponse.from(peopleService.getByPeopleId(peopleId), null);
            return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
        }
        else{
            PublicDetailPeopleResponse response = PublicDetailPeopleResponse.from(peopleService.getByPeopleId(peopleId), null);
            return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
        }
    }

    /**
     * 피플 목록 조회
     *
     * @param pageable 정렬 기준
     * @return 정렬 기준에 해당되는 피플 정보 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<Page<CardPeopleResponse>>> getCardPeoplePage(
        @PageableDefault(sort = "PEOPLE_ID", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CardPeopleResponse> response = peopleService.getCardPeoplePage(pageable);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}