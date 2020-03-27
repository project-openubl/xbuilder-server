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
