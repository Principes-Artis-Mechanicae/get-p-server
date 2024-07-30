package es.princip.getp.domain.client.command.presentation.description;

import es.princip.getp.domain.client.command.domain.BankAccount;
import org.springframework.restdocs.payload.FieldDescriptor;

import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;

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
