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

import org.openublpe.xmlbuilder.core.models.input.standard.note.NoteInputModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoteInputModel_SerieComprobanteAfectadoValidator implements ConstraintValidator<NoteInputModel_SerieComprobanteAfectadoConstraint, NoteInputModel> {

    public static final String message = "Primera letra de Nota y Comprobante Afectado deben de coincidir. Ej. Nota: FC01-1 => Comprobante Afectado: F001-1";

    @Override
    public void initialize(NoteInputModel_SerieComprobanteAfectadoConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(NoteInputModel value, ConstraintValidatorContext context) {
        if (value.getSerie() == null || value.getSerieNumeroComprobanteAfectado() == null) {
            throw new IllegalStateException("Values needed for validation are null. Make sure you call Default.clas validation group before calling this validator");
        }

        boolean isValid = value.getSerie().toUpperCase().startsWith(
                value.getSerieNumeroComprobanteAfectado().toUpperCase().substring(0, 1)
        );

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }

}
