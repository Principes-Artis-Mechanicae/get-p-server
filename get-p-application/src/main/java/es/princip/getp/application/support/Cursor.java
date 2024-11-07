package es.princip.getp.application.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Cursor {

    private final Long id;

    @JsonCreator
    public Cursor(@JsonProperty("id") final Long id) {
        this.id = id;
    }
}
