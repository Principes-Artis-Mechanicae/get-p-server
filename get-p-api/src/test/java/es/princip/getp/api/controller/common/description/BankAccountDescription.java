package es.princip.getp.api.controller.common.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class BankAccountDescription {

    public static FieldDescriptor[] description() {
        return new FieldDescriptor[] {
            fieldWithPath("bankAccount.bank").description("은행명"),
            fieldWithPath("bankAccount.accountNumber").description("계좌번호"),
            fieldWithPath("bankAccount.accountHolder").description("예금주"),
        };
    }
}
