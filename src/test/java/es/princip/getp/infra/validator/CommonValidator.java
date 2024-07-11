package es.princip.getp.infra.validator;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import java.util.stream.IntStream;

public class CommonValidator {
    public static void assertStringListEquals(List<String> expected, List<String> actual) {
        if(expected.size() != actual.size()) {
            fail("실제 값과 기대값의 길이가 다릅니다.");
        }
        IntStream.range(0, expected.size())
            .forEach(i -> assertSoftly(softly -> {
                softly.assertThat(expected.get(i)).isEqualTo(actual.get(i));
        }));
    }
}
