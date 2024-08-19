package es.princip.getp.domain.people.query.presentation.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.docs.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;


public class PublicDetailPeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("peopleId", "피플 ID"),
            getDescriptor("nickname", "닉네임"),
            getDescriptor("profileImageUri", "프로필 이미지 URI"),
            getDescriptor("peopleType", "피플 유형")
                .attributes(key("format").value("TEAM, INDIVIDUAL")),
            getDescriptor("completedProjectsCount", "완수한 프로젝트 수"),
            getDescriptor("likesCount", "받은 좋아요 수"),
            getDescriptor("profile.hashtags[]", "해시태그")
        };
    }
}