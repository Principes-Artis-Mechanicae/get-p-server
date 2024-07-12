package es.princip.getp.domain.member.domain.service;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {

    ProfileImage saveProfileImage(Member member, MultipartFile image);

    void deleteProfileImage(ProfileImage profileImage);
}
