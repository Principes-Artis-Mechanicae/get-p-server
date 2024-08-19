package es.princip.getp.application.member.service;

import es.princip.getp.application.member.command.ChangeProfileImageCommand;
import es.princip.getp.application.member.command.EditMemberCommand;
import es.princip.getp.application.member.port.in.ChangeProfileImageUseCase;
import es.princip.getp.application.member.port.in.EditMemberUseCase;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.member.port.out.UpdateMemberPort;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.ProfileImage;
import es.princip.getp.domain.member.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements EditMemberUseCase, ChangeProfileImageUseCase {

    private final ProfileImageService profileImageService;

    private final UpdateMemberPort updateMemberPort;
    private final LoadMemberPort loadMemberPort;

    @Override
    @Transactional
    public void editMember(final EditMemberCommand command) {
        final Member member = loadMemberPort.loadBy(command.memberId());
        member.edit(command.nickname(), command.phoneNumber());
        updateMemberPort.update(member);
    }

    @Override
    @Transactional
    public String changeProfileImage(final ChangeProfileImageCommand command) {
        final Long memberId = command.memberId();
        final MultipartFile image = command.image();
        final Member member = loadMemberPort.loadBy(memberId);
        if (member.hasProfileImage()) {
            profileImageService.deleteProfileImage(member.getProfileImage());
        }
        ProfileImage profileImage = profileImageService.saveProfileImage(member, image);
        member.changeProfileImage(profileImage);
        updateMemberPort.update(member);
        return profileImage.getUrl();
    }
}
