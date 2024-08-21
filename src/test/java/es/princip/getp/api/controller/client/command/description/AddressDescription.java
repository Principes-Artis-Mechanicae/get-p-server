package es.princip.getp.api.controller.client.command.description;

import es.princip.getp.domain.client.model.Address;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class AddressDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = Address.class;
        return new FieldDescriptor[] {
            getDescriptor("address.zipcode", "우편번호", clazz),
            getDescriptor("address.street", "도로명", clazz),
            getDescriptor("address.detail", "상세 주소", clazz),
        };
    }
}
