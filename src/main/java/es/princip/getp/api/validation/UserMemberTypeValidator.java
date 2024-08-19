package es.princip.getp.api.validation;

import es.princip.getp.domain.member.model.MemberType;
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
