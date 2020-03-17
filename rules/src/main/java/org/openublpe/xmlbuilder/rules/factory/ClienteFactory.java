package org.openublpe.xmlbuilder.rules.factory;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.output.common.ClienteOutputModel;

public class ClienteFactory {

    public static ClienteOutputModel getCliente(ClienteInputModel input) {
        return ClienteOutputModel.Builder.aClienteOutputModel()
                .withNombre(input.getNombre())
                .withNumeroDocumentoIdentidad(input.getNumeroDocumentoIdentidad())
                .withTipoDocumentoIdentidad(Catalog.valueOfCode(Catalog6.class, input.getTipoDocumentoIdentidad()).orElseThrow(Catalog.invalidCatalogValue))
                .withContacto(input.getContacto() != null ? ContactoFactory.getContacto(input.getContacto()) : null)
                .withDireccion(input.getDireccion() != null ? DireccionFactory.getDireccion(input.getDireccion()) : null)
                .build();
    }


}
