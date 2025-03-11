package es.princip.getp.api.controller.client.query.description;

import es.princip.getp.api.controller.client.command.description.AddressDescription;
import es.princip.getp.api.controller.client.command.description.BankAccountDescription;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.ArrayList;
import java.util.List;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class ClientResponseDescription {

    public static FieldDescriptor[] description() {
        final List<FieldDescriptor> descriptions = new ArrayList<>(List.of(
            getDescriptor("clientId", "의뢰자 ID"),
            getDescriptor("email", "이메일"),
            getDescriptor("nickname", "닉네임"),
            getDescriptor("phoneNumber", "전화번호"),
            getDescriptor("profileImageUri", "프로필 이미지 URI"),
            getDescriptor("createdAt", "의뢰자 정보 등록 일시"),
            getDescriptor("updatedAt", "최근 의뢰자 정보 수정 일시")
        ));
        descriptions.addAll(List.of(AddressDescription.description()));
        descriptions.addAll(List.of(BankAccountDescription.description()));
        return descriptions.toArray(new FieldDescriptor[0]);
    }
}
