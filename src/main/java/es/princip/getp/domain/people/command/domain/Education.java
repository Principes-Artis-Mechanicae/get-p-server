package es.princip.getp.domain.people.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education {

    @Column(name = "school")
    private String school;

    @Column(name = "major")
    @NotBlank
    private String major;

    private Education(final String school, final String major) {
        this.school = school;
        this.major = major;
    }

    public static Education of(final String school, final String major) {
        Objects.requireNonNull(major);
        return new Education(school, major);
    }
}
