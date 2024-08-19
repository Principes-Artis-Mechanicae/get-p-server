package es.princip.getp.api.controller.client.command.description;

import es.princip.getp.api.controller.client.command.dto.request.EditMyClientRequest;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.ArrayList;
import java.util.List;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class EditMyClientRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = EditMyClientRequest.class;
        final List<FieldDescriptor> descriptions = new ArrayList<>(List.of(
            getDescriptor("nickname", "닉네임", clazz),
            getDescriptor("email", "이메일", clazz),
            getDescriptor("phoneNumber", "전화번호", clazz)
        ));
        descriptions.addAll(List.of(AddressDescription.description()));
        descriptions.addAll(List.of(BankAccountDescription.description()));
        return descriptions.toArray(new FieldDescriptor[0]);
    }
}
