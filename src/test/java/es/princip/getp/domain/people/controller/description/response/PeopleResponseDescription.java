package es.princip.getp.domain.people.controller.description.response;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.support.FieldDescriptorHelper.getDescriptor;
import static org.springframework.restdocs.snippet.Attributes.key;

public class PeopleResponseDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            getDescriptor("peopleId", "피플 ID"),
            getDescriptor("nickname", "닉네임"),
            getDescriptor("email", "이메일"),
            getDescriptor("phoneNumber", "전화번호"),
            getDescriptor("peopleType", "피플 유형")
                .attributes(key("format").value("TEAM, INDIVIDUAL")),
            getDescriptor("profileImageUri", "프로필 이미지 URI"),
            getDescriptor("createdAt", "피플 정보 등록 일시"),
            getDescriptor("updatedAt", "최근 피플 정보 수정 일시")
        };
    }
}