/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
