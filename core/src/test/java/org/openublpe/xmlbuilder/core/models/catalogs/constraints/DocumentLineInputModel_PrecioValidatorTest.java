/**
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 * <p>
 * Licensed under the Eclipse Public License - v 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.eclipse.org/legal/epl-2.0/
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openublpe.xmlbuilder.core.models.catalogs.constraints;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.input.constraints.DocumentLineInputModel_PrecioValidator;
import org.openublpe.xmlbuilder.core.models.input.constraints.HighLevelGroupValidation;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class DocumentLineInputModel_PrecioValidatorTest {

    @Inject
    Validator validator;

    @Test
    void isValid() {
        DocumentLineInputModel input = DocumentLineInputModel.Builder.aDocumentLineInputModel()
                .build();

        Set<ConstraintViolation<DocumentLineInputModel>> violations = validator.validate(input, HighLevelGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentLineInputModel_PrecioValidator.message))
        );
    }
}
