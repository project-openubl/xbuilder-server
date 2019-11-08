package org.openublpe.xmlbuilder.data;

import org.openublpe.xmlbuilder.models.input.ClienteInputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog6;

public class ClienteInputGenerator {

    private ClienteInputGenerator() {
        // TODO just static methods
    }

    public static ClienteInputModel getClienteConRUC() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Carlos Feria S.A.C.");
        cliente.setNumeroDocumentoIdentidad("12345678912");
        cliente.setTipoDocumentoIdentidad(Catalog6.RUC.toString());
        return cliente;
    }

    public static ClienteInputModel getClienteConDNI() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Carlos Feria");
        cliente.setNumeroDocumentoIdentidad("12345678");
        cliente.setTipoDocumentoIdentidad(Catalog6.DNI.toString());
        return cliente;
    }
}
