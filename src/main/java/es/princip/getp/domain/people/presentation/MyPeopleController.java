package es.princip.getp.domain.people.presentation;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.people.application.PeopleService;
import es.princip.getp.domain.people.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.application.command.UpdatePeopleCommand;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleRepository;
import es.princip.getp.domain.people.presentation.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.presentation.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.presentation.dto.response.people.CreatePeopleResponse;
import es.princip.getp.domain.people.presentation.dto.response.people.PeopleResponse;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiSuccessResult;
import es.princip.getp.infra.security.details.PrincipalDetails;
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

    private final PeopleRepository peopleRepository;
    private final PeopleService peopleService;

    /**
     * 내 피플 정보 등록
     *
     * @param request 등록할 피플 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CreatePeopleResponse>> createMyPeople(
        @RequestBody @Valid CreatePeopleRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getMemberId();
        CreatePeopleCommand command = request.toCommand(memberId);
        peopleService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
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
        People people = peopleRepository.findByMemberId(member.getMemberId()).orElseThrow();
        PeopleResponse response = PeopleResponse.from(people, member);
        return ResponseEntity.ok()
            .body(ApiResponse.success(HttpStatus.OK, response));
    }

    /**
     * 내 피플 정보 수정
     * 
     * @param request 수정할 피플 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> updateMyPeople(
            @RequestBody @Valid UpdatePeopleRequest request,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long memberId = principalDetails.getMember().getMemberId();
        UpdatePeopleCommand command = request.toCommand(memberId);
        peopleService.update(command);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(HttpStatus.CREATED));
    }
}