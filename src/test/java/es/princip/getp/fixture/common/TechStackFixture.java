package es.princip.getp.fixture.common;

import es.princip.getp.domain.common.model.TechStack;

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

    public static List<String> techStacksResponse() {
        return List.of("Java", "Spring", "JPA");
    }
}
