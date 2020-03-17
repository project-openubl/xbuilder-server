package org.openublpe.xmlbuilder.rules.executors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.openublpe.xmlbuilder.rules.EnvironmentVariables;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Interceptor
@OutputValidator
public class OutputValidatorInterceptor {

    @Inject
    Validator validator;

    @ConfigProperty(name = EnvironmentVariables.OUTPUT_VALIDATOR_ENABLE, defaultValue = "false")
    Boolean enable;

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        Object result = ctx.proceed();
        if (enable) {
            Set<ConstraintViolation<Object>> violations = validator.validate(result);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
        return result;
    }

}
