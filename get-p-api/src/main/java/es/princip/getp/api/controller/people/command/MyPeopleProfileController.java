package es.princip.getp.api.controller.people.command;

import es.princip.getp.api.controller.people.command.dto.request.EditPeopleProfileRequest;
import es.princip.getp.api.controller.people.command.dto.request.RegisterPeopleProfileRequest;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.dto.ApiResponse;
import es.princip.getp.api.support.dto.ApiResponse.ApiSuccessResult;
import es.princip.getp.application.people.command.EditPeopleProfileCommand;
import es.princip.getp.application.people.command.RegisterPeopleProfileCommand;
import es.princip.getp.application.people.port.in.EditPeopleProfileUseCase;
import es.princip.getp.application.people.port.in.RegisterPeopleProfileUseCase;
import es.princip.getp.domain.member.model.MemberId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people/me/profile")
@RequiredArgsConstructor
public class MyPeopleProfileController {

    private final PeopleCommandMapper peopleCommandMapper;

    private final RegisterPeopleProfileUseCase registerPeopleProfileUseCase;
    private final EditPeopleProfileUseCase editPeopleProfileUseCase;

    /**
     * 내 피플 프로필 등록
     *
     * @param request 등록할 피플 프로필 정보
     * @return 등록된 피플 프로필 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> registerMyPeopleProfile(
        @RequestBody @Valid final RegisterPeopleProfileRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final RegisterPeopleProfileCommand command = peopleCommandMapper.mapToCommand(memberId, request);
        registerPeopleProfileUseCase.register(command);
        return ApiResponse.success(HttpStatus.CREATED);
    }

    /**
     * 내 피플 프로필 수정
     * 
     * @param request 수정할 피플 프로필 정보
     * @return 수정된 피플 프로필 정보
     */
    @PutMapping
    @PreAuthorize("hasRole('PEOPLE') and isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<?>> editMyPeopleProfile(
        @RequestBody @Valid final EditPeopleProfileRequest request,
        @AuthenticationPrincipal final PrincipalDetails principalDetails
    ) {
        final MemberId memberId = principalDetails.getMember().getId();
        final EditPeopleProfileCommand command = peopleCommandMapper.mapToCommand(memberId, request);
        editPeopleProfileUseCase.edit(command);
        return ApiResponse.success(HttpStatus.OK);
    }
}