package es.princip.getp.domain.client.command.presentation.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;

public class RegisterMyClientRequestDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = RegisterMyClientRequestDescription.class;
        return new FieldDescriptor[] {
            getDescriptor("nickname", "닉네임", clazz),
            getDescriptor("email", "이메일(기본값은 회원 가입 시 기입한 이메일)", clazz)
                .optional(),
            getDescriptor("phoneNumber", "전화번호", clazz),
            getDescriptor("address.zipcode", "우편번호"),
            getDescriptor("address.street", "도로명"),
            getDescriptor("address.detail", "상세 주소"),
            getDescriptor("bankAccount.bank", "은행명"),
            getDescriptor("bankAccount.accountNumber", "계좌번호"),
            getDescriptor("bankAccount.accountHolder", "예금주")
        };
    }
}
