package es.princip.getp.domain.member.annotation;

import es.princip.getp.domain.member.domain.model.MemberType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserMemberTypeValidator implements ConstraintValidator<UserMemberType, MemberType> {
    @Override
    public void initialize(UserMemberType constraintAnnotation) {
    }

    @Override
    public boolean isValid(MemberType value, ConstraintValidatorContext context) {
        return value == MemberType.ROLE_PEOPLE || value == MemberType.ROLE_CLIENT;
    }
}
