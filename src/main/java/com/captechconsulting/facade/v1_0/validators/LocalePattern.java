package com.captechconsulting.facade.v1_0.validators;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Pattern(regexp = "^[a-z]{2}_[A-Z]{2}]")
public @interface LocalePattern {

}
