package com.captechconsulting.facade.v1_0.validators;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Pattern(regexp = "^[A-Za-z -]+$", message = "{error.validation.not_alphabetic}")
public @interface Alphabetic {

}
