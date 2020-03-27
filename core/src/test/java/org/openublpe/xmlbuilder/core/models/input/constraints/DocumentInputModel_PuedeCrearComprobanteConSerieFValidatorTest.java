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
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class DocumentInputModel_PuedeCrearComprobanteConSerieFValidatorTest {

    @Inject
    Validator validator;

    @Test
    void serieFacturaAndDocTribNoDomSinRUC_isInvalid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.DOC_TRIB_NO_DOM_SIN_RUC.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentInputModel_PuedeCrearComprobanteConSerieFValidator.message))
        );
    }

    @Test
    void serieFacturaAndDNI_isInvalid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.DNI.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentInputModel_PuedeCrearComprobanteConSerieFValidator.message))
        );
    }

    @Test
    void serieFacturaAndExtranjeria_isInvalid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.EXTRANJERIA.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentInputModel_PuedeCrearComprobanteConSerieFValidator.message))
        );
    }

    @Test
    void serieFacturaAndRUC_isValid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void serieFacturaAndPasaporte_isInvalid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.PASAPORTE.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentInputModel_PuedeCrearComprobanteConSerieFValidator.message))
        );
    }

    @Test
    void serieFacturaAndDecDiplomatica_isInvalid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("F001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.DEC_DIPLOMATICA.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(
                violations.stream().anyMatch(p -> p.getMessage().equals(DocumentInputModel_PuedeCrearComprobanteConSerieFValidator.message))
        );
    }

    @Test
    void serieBoletaAndDocTribNoDomSinRUC_isValid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("B001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.DOC_TRIB_NO_DOM_SIN_RUC.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void serieBoletaAndDNI_isValid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("B001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.DNI.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void serieBoletaAndExtranjeria_isValid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("B001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.EXTRANJERIA.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void serieBoletaAndRUC_isValid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("B001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.RUC.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void serieBoletaAndPasaporte_isValid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("B001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.PASAPORTE.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(violations.isEmpty());
    }

    @Test
    void serieBoletaAndDecDiplomatica_isValid() {
        InvoiceInputModel input = InvoiceInputModel.Builder.anInvoiceInputModel()
                .withSerie("B001")
                .withCliente(ClienteInputModel.Builder.aClienteInputModel()
                        .withTipoDocumentoIdentidad(Catalog6.PASAPORTE.toString())
                        .build()
                )
                .build();

        Set<ConstraintViolation<InvoiceInputModel>> violations = validator.validate(input, DocumentInputModel_PuedeCrearComprobanteConSerieFGroupValidation.class);
        assertTrue(violations.isEmpty());
    }
}
