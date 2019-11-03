package org.openublpe.xmlbuilder;

import org.openublpe.xmlbuilder.models.input.FirmanteInputModel;

public class FirmanteInputGenerator {

    private FirmanteInputGenerator() {
        // TODO just static methods
    }

    public static FirmanteInputModel getFirmante() {
        FirmanteInputModel result = new FirmanteInputModel();
        result.setRuc("98765432198");
        result.setRazonSocial("Softgreen S.A.C.");
        return result;
    }
}
