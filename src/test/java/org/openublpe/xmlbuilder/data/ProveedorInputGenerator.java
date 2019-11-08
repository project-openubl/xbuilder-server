package org.openublpe.xmlbuilder.data;

import org.openublpe.xmlbuilder.models.input.ProveedorInputModel;

public class ProveedorInputGenerator {

    private ProveedorInputGenerator() {
        // TODO just static methods
    }

    public static ProveedorInputModel getProveedor() {
        ProveedorInputModel proveedor = new ProveedorInputModel();
        proveedor.setRuc("12345678912");
        proveedor.setCodigoPostal("050101");
        proveedor.setRazonSocial("Wolsnut4 S.A.C.");
        return proveedor;
    }
}
