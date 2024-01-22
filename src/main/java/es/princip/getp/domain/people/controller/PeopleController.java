package es.princip.getp.domain.people.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.response.PeopleResponse;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {
    private final PeopleService peopleService;

    /**
     * 피플 계정 생성
     * 
     * @param CreatePeopleRequest 생성할 피플 정보
     * @return PeopleResponseDTO 생성 완료된 피플 정보
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PeopleResponse>> create(
            @RequestBody @Valid CreatePeopleRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        PeopleResponse response = PeopleResponse.from(peopleService.create(member, request));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.CREATED, response));
    }

    /**
     * 피플 상세 조회
     * 
     * @param peopleId 피플 ID
     * @return PeopleResponseDTO 피플 ID에 해당되는 피플 정보
     */
    @GetMapping("/{peopleId}")
    public ResponseEntity<ApiSuccessResult<PeopleResponse>> getPeople(@PathVariable Long peopleId) {
        PeopleResponse response = PeopleResponse.from(peopleService.getByPeopleId(peopleId));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 피플 목록 조회
     * 
     * @param pageable 정렬 기준
     * @return Page<PeopleResponseDTO> 정렬 기준에 해당되는 피플 정보 목록
     */
    @GetMapping
    public ResponseEntity<ApiSuccessResult<Page<PeopleResponse>>> getPeoplePage(@PageableDefault(size = 10,
            sort = "CREATED_AT", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PeopleResponse> response = peopleService.getPeoplePage(pageable);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}
