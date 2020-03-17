package org.openublpe.xmlbuilder.rules.factory;

import org.openublpe.xmlbuilder.core.models.input.common.DireccionInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.DireccionOutputModel;

public class DireccionFactory {

    public static DireccionOutputModel getDireccion(DireccionInputModel input) {
        return DireccionOutputModel.Builder.aDireccionOutputModel()
                .withUbigeo(input.getUbigeo())
                .withDireccion(input.getDireccion())
                .withDepartamento(input.getDepartamento())
                .withProvincia(input.getProvincia())
                .withDistrito(input.getDistrito())
                .withCodigoLocal(input.getCodigoLocal())
                .withUrbanizacion(input.getUrbanizacion())
                .withCodigoPais(input.getCodigoPais())
                .build();
    }

}
