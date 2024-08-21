package es.princip.getp.domain.client.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("주소")
class AddressTest {

    @Nested
    class 주소_생성 {

        private final String 우편번호 = "41566";
        private final String 도로명 = "대구광역시 북구 대학로 80";
        private final String 상세주소 = "IT대학 융복합관";

        @Test
        void 상세주소는_생략할_수_없다() {

            final Address address = new Address(우편번호, 도로명, null);

            final String 주소 = address.toString();

            assertThat(주소).isEqualTo("41566 대구광역시 북구 대학로 80");
        }

        @Test
        void 주소는_우편번호_도로명_상세주소로_구성된다() {
            final Address address = new Address(우편번호, 도로명, 상세주소);

            final String 주소 = address.toString();

            assertThat(주소).isEqualTo("41566 대구광역시 북구 대학로 80 IT대학 융복합관");
        }
    }
}