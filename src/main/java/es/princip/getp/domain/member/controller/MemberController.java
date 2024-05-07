package es.princip.getp.domain.member.controller;

import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.member.dto.response.MemberResponse;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static es.princip.getp.global.util.ApiResponse.ApiSuccessResult;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final PeopleService peopleService;
    private final ClientService clientService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<MemberResponse>> getMyMember(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Member member = principalDetails.getMember();
        Long memberId = member.getMemberId();
        String email = member.getEmail();
        MemberType memberType = member.getMemberType();
        String nickname = memberType.isClient() ? clientService.getByMemberId(memberId).getNickname() :
                          memberType.isPeople() ? peopleService.getByMemberId(memberId).getNickname() :
                          email.substring(0, email.indexOf('@'));
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, MemberResponse.of(member, nickname)));
    }
}
