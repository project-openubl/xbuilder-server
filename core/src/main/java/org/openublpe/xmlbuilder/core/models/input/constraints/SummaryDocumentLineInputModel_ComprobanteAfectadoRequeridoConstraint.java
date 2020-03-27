package org.openublpe.xmlbuilder.core.models.input.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
