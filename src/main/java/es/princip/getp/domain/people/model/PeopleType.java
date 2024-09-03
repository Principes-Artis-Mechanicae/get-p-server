package es.princip.getp.domain.people.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum PeopleType {
    TEAM, INDIVIDUAL;

    @JsonCreator
    public static PeopleType parsing(String inputValue) {
        return Stream.of(PeopleType.values())
            .filter(peopleType -> peopleType.toString().equals(inputValue.toUpperCase()))
            .findFirst()
            .orElse(null);
    }
}
