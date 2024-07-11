package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CreatePeopleResponse;
import es.princip.getp.domain.people.dto.response.people.PeopleResponse;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people/me")
@RequiredArgsConstructor
public class MyPeopleController {

    private final PeopleService peopleService;

    /**
     * 내 피플 정보 등록
     *
     * @param request 등록할 피플 정보
     * @return 등록된 피플 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CreatePeopleResponse>> createMyPeople(
        @RequestBody @Valid CreatePeopleRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getMemberId();
        CreatePeopleResponse response = CreatePeopleResponse.from(peopleService.create(memberId, request));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED, response));
    }

    /**
     * 내 피플 정보 조회
     *
     * @return 내 피플 정보
     */
    @GetMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PeopleResponse>> getMyPeople(
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        PeopleResponse response = PeopleResponse.from(peopleService.getByMemberId(member.getMemberId()));
        return ResponseEntity.ok()
            .body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 피플 정보 수정
     * 
     * @param request 수정할 피플 정보
     * @return 수정된 피플 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> updateMyPeople(
            @RequestBody @Valid UpdatePeopleRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        peopleService.update(member.getMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }
}