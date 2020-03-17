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
package org.openublpe.xmlbuilder.core.models.output.constraints;


import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineImpuestosOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentLineOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentMonetaryTotalOutputModel;
import org.openublpe.xmlbuilder.core.models.output.standard.DocumentOutputModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class DocumentOutputModelValidator implements ConstraintValidator<DocumentOutputModelConstraint, DocumentOutputModel> {

    @Override
    public void initialize(DocumentOutputModelConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(DocumentOutputModel value, ConstraintValidatorContext context) {
        BigDecimal totalValorVenta = value.getDetalle().stream()
                .map(DocumentLineOutputModel::getValorVentaSinImpuestos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalImpuestos = value.getDetalle().stream()
                .map(DocumentLineOutputModel::getImpuestos)
                .map(DocumentLineImpuestosOutputModel::getImporteTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        DocumentMonetaryTotalOutputModel totales = value.getTotales();
        if (totales.getImporteTotal().compareTo(totalValorVenta.add(totalImpuestos)) == 0) {
            return true;
        }

        return false;
    }

}
