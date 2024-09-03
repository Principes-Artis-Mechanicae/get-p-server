package es.princip.getp.domain.client.model;

import es.princip.getp.domain.common.exception.NotValidDomainModelException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("계좌")
class BankAccountTest {

    @Nested
    class 계좌_생성 {

        private final String 은행 = "국민은행";
        private final String 계좌번호 = "123456-78-123456";
        private final String 예금주 = "홍길동";

        @Test
        void 계좌는_은행_계좌번호_예금주로_구성된다() {
            final BankAccount bankAccount = new BankAccount(은행, 계좌번호, 예금주);

            assertThat(bankAccount.getBank()).isEqualTo(은행);
            assertThat(bankAccount.getAccountNumber()).isEqualTo(계좌번호);
            assertThat(bankAccount.getAccountHolder()).isEqualTo(예금주);
        }

        @Test
        void 은행은_생략될_수_없다() {
            assertThatThrownBy(() -> new BankAccount(null, 계좌번호, 예금주))
                .isInstanceOf(NotValidDomainModelException.class);
        }

        @Test
        void 계좌번호는_생략될_수_없다() {
            assertThatThrownBy(() -> new BankAccount(은행, null, 예금주))
                .isInstanceOf(NotValidDomainModelException.class);
        }

        @Test
        void 예금주는_생략될_수_없다() {
            assertThatThrownBy(() -> new BankAccount(은행, 계좌번호, null))
                .isInstanceOf(NotValidDomainModelException.class);
        }
    }
}