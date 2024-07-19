package es.princip.getp.domain.people.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education {

    @Column(name = "school")
    private String school;

    @Column(name = "major", nullable = false)
    @NotBlank
    private String major;

    private Education(final String school, final String major) {
        this.school = school;
        this.major = major;
    }

    public static Education of(final String school, final String major) {
        if (major == null) {
            throw new IllegalArgumentException("major is null");
        }
        return new Education(school, major);
    }
}
