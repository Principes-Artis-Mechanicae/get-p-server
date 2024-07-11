package es.princip.getp.domain.people.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education {

    @Column(name = "school")
    private String school;

    @Column(name = "major")
    @NotBlank
    private String major;

    private Education(String school, String major) {
        this.school = school;
        this.major = major;
    }

    public static Education of(String school, String major) {
        return new Education(school, major);
    }
}
