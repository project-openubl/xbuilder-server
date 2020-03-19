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
package org.openublpe.xmlbuilder.core.models.input.constraints;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentInputModel;
import org.openublpe.xmlbuilder.core.models.utils.RegexUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentInputModel_PuedeCrearFacturaValidator implements ConstraintValidator<DocumentInputModel_PuedeCrearFacturaConstraint, DocumentInputModel> {

    public static final String message = "Las Facturas sólo pueden ser creadas por clientes con RUC";

    @Override
    public void initialize(DocumentInputModel_PuedeCrearFacturaConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(DocumentInputModel value, ConstraintValidatorContext context) {
        if (value.getSerie() == null || value.getCliente() == null || value.getCliente().getTipoDocumentoIdentidad() == null) {
            throw new IllegalStateException("Values needed for validation are null. Make sure you call Default.clas validation group before calling this validator");
        }

        String serie = value.getSerie();
        Catalog6 catalog6 = Catalog.valueOfCode(Catalog6.class, value.getCliente().getTipoDocumentoIdentidad())
                .orElseThrow(Catalog.invalidCatalogValue);

        boolean isInvalid = RegexUtils.FACTURA_SERIE_REGEX.matcher(serie).find() && !catalog6.equals(Catalog6.RUC);

        if (isInvalid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return !isInvalid;
    }

}
