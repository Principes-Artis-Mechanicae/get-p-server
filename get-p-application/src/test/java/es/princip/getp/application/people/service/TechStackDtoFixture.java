package es.princip.getp.application.people.service;

import es.princip.getp.domain.common.model.TechStack;

import java.util.List;

import static es.princip.getp.fixture.common.TechStackFixture.techStacks;

public class TechStackDtoFixture {

    public static List<String> techStacksRequest() {
        return techStacks().stream()
            .map(TechStack::getValue)
            .toList();
    }

    public static List<String> techStacksResponse() {
        return techStacks().stream()
            .map(TechStack::getValue)
            .toList();
    }
}
