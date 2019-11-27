package org.openublpe.xmlbuilder.models.output.sunat;

import org.openublpe.xmlbuilder.models.catalogs.Catalog5;
import org.openublpe.xmlbuilder.models.output.standard.ImpuestoTotalOutputModel;

import java.math.BigDecimal;

public class ImpuestoTotalResumenDiarioOutputModel extends ImpuestoTotalOutputModel {

    public ImpuestoTotalResumenDiarioOutputModel() {
    }

    public ImpuestoTotalResumenDiarioOutputModel(Catalog5 categoria, BigDecimal importe) {
        super(categoria, importe);
    }
}
