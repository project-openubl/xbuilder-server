package org.openublpe.xmlbuilder.data;

import org.openublpe.xmlbuilder.models.input.FirmanteInputModel;

public class FirmanteInputGenerator {

    private FirmanteInputGenerator() {
        // TODO just static methods
    }

    public static FirmanteInputModel getFirmante() {
        FirmanteInputModel result = new FirmanteInputModel();
        result.setRuc("10467793549");
        result.setRazonSocial("SoftGreen S.A.C.");
        return result;
    }
}
