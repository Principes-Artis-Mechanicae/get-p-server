package es.princip.getp.domain.member.command.application;

import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.command.application.command.CreateMemberCommand;
import es.princip.getp.domain.member.command.application.command.UpdateMemberCommand;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberRepository;
import es.princip.getp.domain.member.command.domain.model.ProfileImage;
import es.princip.getp.domain.member.command.domain.service.ProfileImageService;
import es.princip.getp.domain.member.command.domain.service.ServiceTermAgreementService;
import es.princip.getp.infra.exception.BusinessLogicException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final ServiceTermAgreementService agreementService;
    private final ProfileImageService profileImageService;

    private final MemberRepository memberRepository;

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
            () -> new EntityNotFoundException("해당 회원은 존재하지 않습니다.")
        );
    }

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
        Member member = findById(command.memberId());
        member.changeNickname(command.nickname());
        member.changePhoneNumber(command.phoneNumber());
    }

    @Transactional
    public String changeProfileImage(Long memberId, MultipartFile image) {
        Member member = findById(memberId);
        if (member.hasProfileImage()) {
            profileImageService.deleteProfileImage(member.getProfileImage());
        }
        ProfileImage profileImage = profileImageService.saveProfileImage(member, image);
        member.changeProfileImage(profileImage);
        return profileImage.getUri();
    }
}
