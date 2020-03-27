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
package org.openublpe.xmlbuilder.core.models.input.constraints;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentComprobanteAfectadoInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentComprobanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidatorTest {

    @Inject
    Validator validator;

    @Test
    void comprobanteEsBoleta_comprobanteAfectadoNull_isValid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.BOLETA.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void comprobanteEsNotaCredito_comprobanteAfectadoNotnull_isValid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_CREDITO.toString())
                        .build()
                )
                .withComprobanteAfectado(SummaryDocumentComprobanteAfectadoInputModel.Builder.aSummaryDocumentComprobanteAfectadoInputModel()
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void comprobanteEsNotaDebito_comprobanteAfectadoNotnull_isValid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_DEBITO.toString())
                        .build()
                )
                .withComprobanteAfectado(SummaryDocumentComprobanteAfectadoInputModel.Builder.aSummaryDocumentComprobanteAfectadoInputModel()
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void comprobanteEsNotaCredito_comprobanteAfectadoNull_isInvalid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_CREDITO.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidator.message))
        );
    }

    @Test
    void comprobanteEsNotaDebito_comprobanteAfectadoNull_isInvalid() {
        SummaryDocumentLineInputModel input = SummaryDocumentLineInputModel.Builder.aSummaryDocumentLineInputModel()
                .withComprobante(SummaryDocumentComprobanteInputModel.Builder.aSummaryDocumentComprobanteInputModel()
                        .withTipo(Catalog1.NOTA_DEBITO.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<SummaryDocumentLineInputModel>> violations = validator.validate(input, SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidator.message))
        );
    }
}
