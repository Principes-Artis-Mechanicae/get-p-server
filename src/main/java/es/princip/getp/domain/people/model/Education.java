package es.princip.getp.domain.people.model;

import es.princip.getp.domain.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Education extends BaseModel {

    private final String school;
    @NotBlank private final String major;

    private Education(final String school, final String major) {
        this.school = school;
        this.major = major;

        validate();
    }

    public static Education of(final String school, final String major) {
        return new Education(school, major);
    }
}
