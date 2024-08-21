package es.princip.getp.fixture.common;

import es.princip.getp.common.domain.TechStack;
import es.princip.getp.common.dto.TechStacksResponse;

import java.util.List;

public class TechStackFixture {

    public static List<TechStack> techStacks() {
        return List.of(
            TechStack.from("Java"),
            TechStack.from("Spring"),
            TechStack.from("JPA")
        );
    }

    public static List<String> techStacksRequest() {
        return List.of("Java", "Spring", "JPA");
    }

    public static TechStacksResponse techStacksResponse() {
        return TechStacksResponse.from(
            List.of(
                TechStack.from("Java"),
                TechStack.from("Spring"),
                TechStack.from("JPA")
            )
        );
    }
}
