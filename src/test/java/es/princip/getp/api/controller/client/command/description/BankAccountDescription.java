package es.princip.getp.api.controller.client.command.description;

import org.springframework.restdocs.payload.FieldDescriptor;

import es.princip.getp.domain.common.model.BankAccount;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;

public class BankAccountDescription {

    public static FieldDescriptor[] description() {
        final Class<?> clazz = BankAccount.class;
        return new FieldDescriptor[] {
            getDescriptor("bankAccount.bank", "은행명", clazz),
            getDescriptor("bankAccount.accountNumber", "계좌번호", clazz),
            getDescriptor("bankAccount.accountHolder", "예금주", clazz)
        };
    }
}
