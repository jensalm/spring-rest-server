package com.captechconsulting.facade.v1_0.validators;

import com.captechconsulting.facade.v1_0.data.TicketVO;
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
@Constraint(validatedBy = MatchingPackageNumbers.MatchingPackageNumbersValidator.class)
@Documented
public @interface MatchingPackageNumbers {

    String message() default "{error.validation.package_number_do_not_match}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public static class MatchingPackageNumbersValidator implements ConstraintValidator<MatchingPackageNumbers, TicketVO> {

        private MatchingPackageNumbers matchingPackageNumbers;

        @Override
        public void initialize(MatchingPackageNumbers matchingPackageNumbers) {
            this.matchingPackageNumbers = matchingPackageNumbers;
        }

        @Override
        public boolean isValid(TicketVO ticket, ConstraintValidatorContext constraintValidatorContext) {

            if (StringUtils.isBlank(ticket.getPackageNumber()) && StringUtils.isBlank(ticket.getConfirmPackageNumber())) {
                return true;
            }

            if (ticket.getPackageNumber().equals(ticket.getConfirmPackageNumber())) {
                return true;
            }

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(matchingPackageNumbers.message()).addConstraintViolation();
            return false;
        }

    }
}
