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

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validations that are executed after {@link javax.validation.groups.Default} group
 */
public class SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoValidator implements ConstraintValidator<SummaryDocumentLineInputModel_ComprobanteAfectadoRequeridoConstraint, SummaryDocumentLineInputModel> {

    public static final String message = "Si es Nota Crédito/Débito entonces debes de incluir el campo 'comprobanteAfectado'";

    @Override
    public boolean isValid(SummaryDocumentLineInputModel value, ConstraintValidatorContext context) {
        if (value.getComprobante() == null || value.getComprobante().getTipo() == null) {
            throw new IllegalStateException("Values needed for validation are null. Make sure you call Default.clas validation group before calling this validator");
        }

        Catalog1 catalog1 = Catalog.valueOfCode(Catalog1.class, value.getComprobante().getTipo())
                .orElseThrow(Catalog.invalidCatalogValue);

        boolean isInvalid = value.getComprobanteAfectado() == null
                && (catalog1.equals(Catalog1.NOTA_CREDITO) || catalog1.equals(Catalog1.NOTA_DEBITO));

        if (isInvalid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return !isInvalid;
    }

}
