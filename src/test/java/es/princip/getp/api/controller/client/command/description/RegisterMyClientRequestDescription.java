package es.princip.getp.api.controller.client.command.description;

import es.princip.getp.api.controller.client.command.dto.request.RegisterMyClientRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.ArrayList;
import java.util.List;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class RegisterMyClientRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = RegisterMyClientRequest.class;
        final List<FieldDescriptor> descriptions = new ArrayList<>(List.of(
            getDescriptor("nickname", "닉네임", clazz),
            getDescriptor("email", "이메일. 미입력 시 회원 정보의 이메일로 등록됩니다.", clazz),
            getDescriptor("phoneNumber", "전화번호", clazz)
        ));
        descriptions.addAll(List.of(AddressDescription.description()));
        return descriptions.toArray(new FieldDescriptor[0]);
    }
}
