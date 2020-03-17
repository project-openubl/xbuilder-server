package org.openublpe.xmlbuilder.rules.factory;

import org.openublpe.xmlbuilder.core.models.input.common.FirmanteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.FirmanteOutputModel;

public class FirmanteFactory {

    public static FirmanteOutputModel getFirmante(FirmanteInputModel input) {
        return FirmanteOutputModel.Builder.aFirmanteOutputModel()
                .withRuc(input.getRuc())
                .withRazonSocial(input.getRazonSocial())
                .build();
    }

    public static FirmanteOutputModel getFirmante(ProveedorInputModel input) {
        return FirmanteOutputModel.Builder.aFirmanteOutputModel()
                .withRuc(input.getRuc())
                .withRazonSocial(input.getRazonSocial())
                .build();
    }
}
