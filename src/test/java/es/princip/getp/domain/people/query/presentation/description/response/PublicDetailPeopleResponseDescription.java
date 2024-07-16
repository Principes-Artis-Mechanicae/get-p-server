package es.princip.getp.domain.people.query.presentation.description.response;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;


public class PublicDetailPeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("peopleId", "피플 ID"),
            getDescriptor("member.nickname", "닉네임"),
            getDescriptor("peopleType", "피플 유형")
                .attributes(key("format").value("TEAM, INDIVIDUAL")),
            getDescriptor("member.profileImageUri", "프로필 이미지 URI"),
            getDescriptor("profile.hashtags[]", "해시태그"),
            getDescriptor("profile.completedProjectsCount", "완수한 프로젝트 수"),
            getDescriptor("profile.interestsCount", "받은 관심 수")
        };
    }
}