package es.princip.getp.api.controller.people.command;

import es.princip.getp.api.controller.people.command.dto.request.EditPeopleRequest;
import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleRequest;
import es.princip.getp.api.controller.people.command.dto.response.RegisterPeopleResponse;
import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.people.command.EditPeopleCommand;
import es.princip.getp.application.people.command.RegisterPeopleCommand;
import es.princip.getp.application.people.port.in.EditPeopleUseCase;
import es.princip.getp.application.people.port.in.RegisterPeopleUseCase;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
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

    private final RegisterPeopleUseCase registerPeopleUseCase;
    private final EditPeopleUseCase editPeopleUseCase;

    /**
     * 내 피플 정보 등록
     *
     * @param request 등록할 피플 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<RegisterPeopleResponse>> registerMyPeople(
        @RequestBody @Valid final RegisterPeopleRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final Member member = principalDetails.getMember();
        final RegisterPeopleCommand command = request.toCommand(member);
        final Long peopleId = registerPeopleUseCase.register(command).getValue();
        final RegisterPeopleResponse response = new RegisterPeopleResponse(peopleId);
        return ApiResponse.success(HttpStatus.CREATED, response);
    }

    /**
     * 내 피플 정보 수정
     * 
     * @param request 수정할 피플 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> editMyPeople(
            @RequestBody @Valid final EditPeopleRequest request,
            @AuthenticationPrincipal final PrincipalDetails principalDetails) {
        final MemberId memberId = principalDetails.getMember().getId();
        final EditPeopleCommand command = request.toCommand(memberId);
        editPeopleUseCase.edit(command);
        return ApiResponse.success(HttpStatus.CREATED);
    }
}