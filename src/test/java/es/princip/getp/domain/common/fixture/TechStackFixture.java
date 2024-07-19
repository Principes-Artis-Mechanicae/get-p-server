package es.princip.getp.domain.common.fixture;

import es.princip.getp.domain.common.domain.TechStack;
import es.princip.getp.domain.common.dto.TechStacksResponse;

import java.util.List;

public class TechStackFixture {

    public static List<TechStack> techStacks() {
        return List.of(
            TechStack.of("Java"),
            TechStack.of("Spring"),
            TechStack.of("JPA")
        );
    }

    public static List<String> techStacksRequest() {
        return List.of("Java", "Spring", "JPA");
    }

    public static TechStacksResponse techStacksResponse() {
        return TechStacksResponse.from(
            List.of(
                TechStack.of("Java"),
                TechStack.of("Spring"),
                TechStack.of("JPA")
            )
        );
    }
}
