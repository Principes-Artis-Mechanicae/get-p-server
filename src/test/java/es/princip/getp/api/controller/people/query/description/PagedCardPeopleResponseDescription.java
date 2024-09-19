package es.princip.getp.api.controller.people.query.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;


public class PagedCardPeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("content[].peopleId", "피플 ID"),
            getDescriptor("content[].peopleType", "피플 유형")
                .attributes(key("format").value("TEAM, INDIVIDUAL")),
            getDescriptor("content[].nickname", "닉네임"),
            getDescriptor("content[].profileImageUri", "프로필 이미지 URI"),
            getDescriptor("content[].completedProjectsCount", "완수한 프로젝트 수"),
            getDescriptor("content[].likesCount", "받은 좋아요 수"),
            getDescriptor("content[].profile.introduction", "소개"),
            getDescriptor("content[].profile.activityArea", "활동 지역"),
            getDescriptor("content[].profile.hashtags[]", "해시태그")
        };
    }
}