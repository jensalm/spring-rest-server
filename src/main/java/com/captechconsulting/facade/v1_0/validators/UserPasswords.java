package com.captechconsulting.facade.v1_0.validators;

import com.captechconsulting.facade.v1_0.data.UserVO;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UserPasswords.PasswordsValidator.class)
@Documented
public @interface UserPasswords {

    String message() default "{error.validation.password_do_not_match}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public static class PasswordsValidator implements ConstraintValidator<UserPasswords, UserVO> {

        private UserPasswords userPasswords;

        @Override
        public void initialize(UserPasswords userPasswords) {
            this.userPasswords = userPasswords;
        }

        @Override
        public boolean isValid(UserVO user, ConstraintValidatorContext constraintValidatorContext) {

            if (StringUtils.isBlank(user.getPassword()) && StringUtils.isBlank(user.getConfirmPassword())) {
                return true;
            }

            if (user.getPassword().equals(user.getConfirmPassword())) {
                return true;
            }

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(userPasswords.message()).addConstraintViolation();
            return false;
        }

    }
}
