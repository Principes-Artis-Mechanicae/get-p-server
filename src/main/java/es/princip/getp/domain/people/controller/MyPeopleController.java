package es.princip.getp.domain.people.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequestDTO;
import es.princip.getp.domain.people.dto.response.PeopleResponseDTO;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiSuccessResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/people/me")
@RequiredArgsConstructor
public class MyPeopleController {
    private final PeopleService peopleService;

    /**
     * 내 피플 정보 조회
     * 
     * @return PeopleResponseDTO 내 피플 기본 정보
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PeopleResponseDTO>> getMyPeople(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        PeopleResponseDTO response = peopleService.getResponseByMemberId(member.getMemberId());
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 피플 정보 수정
     * 
     * @param UpdatePeopleRequestDTO 수정할 피플 정보
     * @return PeopleResponseDTO 수정 완료된 피플 정보
     */
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<PeopleResponseDTO>> updatePeopleByPeopleId(
            @RequestBody @Valid UpdatePeopleRequestDTO request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();
        PeopleResponseDTO response = peopleService.update(member.getMemberId(), request);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, response));
    }
}
