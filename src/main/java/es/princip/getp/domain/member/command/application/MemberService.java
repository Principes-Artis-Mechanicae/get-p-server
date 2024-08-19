package es.princip.getp.domain.member.command.application;

import es.princip.getp.domain.member.command.application.command.UpdateMemberCommand;
import es.princip.getp.domain.member.command.application.port.out.LoadMemberPort;
import es.princip.getp.domain.member.command.application.port.out.UpdateMemberPort;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.ProfileImage;
import es.princip.getp.domain.member.command.domain.service.ProfileImageService;
import es.princip.getp.persistence.adapter.member.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final ProfileImageService profileImageService;

    private final UpdateMemberPort updateMemberPort;
    private final LoadMemberPort loadMemberPort;

    /**
     * 회원 정보를 수정한다.
     *
     * @param command 회원 정보 수정 명령
     * @throws NotFoundMemberException 해당 회원이 존재하지 않는 경우
     */
    @Transactional
    public void update(final UpdateMemberCommand command) {
        final Member member = loadMemberPort.loadBy(command.memberId());
        member.edit(command.nickname(), command.phoneNumber());
        updateMemberPort.update(member);
    }

    /**
     * 회원의 프로필 이미지를 수정한다. 기존의 프로필 이미지가 존재하는 경우 삭제한다.
     *
     * @param memberId 회원 식별자
     * @param image 프로필 이미지
     * @throws NotFoundMemberException 해당 회원이 존재하지 않는 경우
     * @return 수정된 프로필 이미지의 URI
     */
    @Transactional
    public String changeProfileImage(final Long memberId, final MultipartFile image) {
        Member member = loadMemberPort.loadBy(memberId);
        if (member.hasProfileImage()) {
            profileImageService.deleteProfileImage(member.getProfileImage());
        }
        ProfileImage profileImage = profileImageService.saveProfileImage(member, image);
        member.changeProfileImage(profileImage);
        updateMemberPort.update(member);
        return profileImage.getUrl();
    }
}
