package org.openublpe.xmlbuilder.rules.factory;

import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ProveedorOutputModel;

public class ProveedorFactory {

    public static ProveedorOutputModel getProveedor(ProveedorInputModel input) {
        return ProveedorOutputModel.Builder.aProveedorOutputModel()
                .withRuc(input.getRuc())
                .withRazonSocial(input.getRazonSocial())
                .withNombreComercial(input.getNombreComercial())
                .withDireccion(input.getDireccion() != null ? DireccionFactory.getDireccion(input.getDireccion()) : null)
                .withContacto(input.getContacto() != null ? ContactoFactory.getContacto(input.getContacto()) : null)
                .build();

    }

}
