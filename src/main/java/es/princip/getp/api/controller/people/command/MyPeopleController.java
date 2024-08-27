package es.princip.getp.api.controller.people.command;

import es.princip.getp.api.controller.ApiResponse;
import es.princip.getp.api.controller.ApiResponse.ApiSuccessResult;
import es.princip.getp.api.controller.people.command.dto.request.CreatePeopleRequest;
import es.princip.getp.api.controller.people.command.dto.request.UpdatePeopleRequest;
import es.princip.getp.api.controller.people.command.dto.response.CreatePeopleResponse;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.domain.people.command.application.PeopleService;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;
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
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<CreatePeopleResponse>> createMyPeople(
        @RequestBody @Valid final CreatePeopleRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final CreatePeopleCommand command = request.toCommand(memberId);
        final Long peopleId = peopleService.create(command);
        final CreatePeopleResponse response = new CreatePeopleResponse(peopleId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }

    /**
     * 내 피플 정보 수정
     * 
     * @param request 수정할 피플 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> updateMyPeople(
            @RequestBody @Valid final UpdatePeopleRequest request,
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Long memberId = principalDetails.getMember().getMemberId();
        final UpdatePeopleCommand command = request.toCommand(memberId);
        peopleService.update(command);
        return ApiResponse.success(HttpStatus.CREATED);
    }
}