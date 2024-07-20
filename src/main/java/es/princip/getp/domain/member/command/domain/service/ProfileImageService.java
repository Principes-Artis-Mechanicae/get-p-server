package es.princip.getp.domain.member.command.domain.service;

import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.ProfileImage;
import es.princip.getp.domain.member.command.exception.FailedToSaveProfileImageException;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {

    /**
     * 회원의 프로필 이미지를 저장한다.
     *
     * @param member 회원
     * @param image 프로필 이미지 MultiPartFile
     * @throws FailedToSaveProfileImageException 프로필 이미지 저장에 실패한 경우
     * @return 저장된 프로필 이미지
     */
    ProfileImage saveProfileImage(Member member, MultipartFile image);

    /**
     * 프로필 이미지를 삭제한다.
     *
     * @param profileImage 삭제할 프로필 이미지
     */
    void deleteProfileImage(ProfileImage profileImage);
}
