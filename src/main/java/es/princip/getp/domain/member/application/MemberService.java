package es.princip.getp.domain.member.application;

import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.application.command.CreateMemberCommand;
import es.princip.getp.domain.member.application.command.UpdateMemberCommand;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.MemberRepository;
import es.princip.getp.domain.member.domain.model.ProfileImage;
import es.princip.getp.domain.member.domain.service.ServiceTermAgreementService;
import es.princip.getp.domain.member.infra.ProfileImageServiceImpl;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final ServiceTermAgreementService agreementService;
    private final ProfileImageServiceImpl profileImageService;

    private final MemberRepository memberRepository;

    @Transactional
    public Long create(CreateMemberCommand command) {
        if (memberRepository.existsByEmail(command.email()))
            throw new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL);
        final Member member = Member.of(command.email(), command.password(), command.memberType());
        agreementService.agreeServiceTerms(member, command.serviceTerms());
        Member created = memberRepository.save(member);
        return created.getMemberId();
    }

    @Transactional
    public void update(UpdateMemberCommand command) {
        Member member = memberRepository.findById(command.memberId()).orElseThrow();
        member.changeNickname(command.nickname());
        member.changePhoneNumber(command.phoneNumber());
    }

    @Transactional
    public URI changeProfileImage(Long memberId, MultipartFile image) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (member.hasProfileImage()) {
            profileImageService.deleteProfileImage(member.getProfileImage());
        }
        ProfileImage profileImage = profileImageService.saveProfileImage(member, image);
        member.changeProfileImage(profileImage);
        return profileImage.getUri();
    }
}
