package org.openublpe.xmlbuilder.inputdata.generator.cases.proveedorsincodigopostal;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog6;
import org.openublpe.xmlbuilder.core.models.input.common.ClienteInputModel;
import org.openublpe.xmlbuilder.core.models.input.common.ProveedorInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.DocumentLineInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private Utils(){
        // Just static access
    }

    public static ProveedorInputModel getProveedor() {
        ProveedorInputModel proveedor = new ProveedorInputModel();
        proveedor.setRuc("22222222222");
        proveedor.setRazonSocial("Proveedor");
        return proveedor;
    }

    public static ClienteInputModel getCliente() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Cliente");
        cliente.setNumeroDocumentoIdentidad("33333333333");
        cliente.setTipoDocumentoIdentidad(Catalog6.RUC.toString());
        return cliente;
    }

    public static ClienteInputModel getClienteConDNI() {
        ClienteInputModel cliente = new ClienteInputModel();
        cliente.setNombre("Cliente");
        cliente.setNumeroDocumentoIdentidad("33333333");
        cliente.setTipoDocumentoIdentidad(Catalog6.DNI.toString());
        return cliente;
    }

    public static List<DocumentLineInputModel> getDetalle() {
        List<DocumentLineInputModel> detalle = new ArrayList<>();

        DocumentLineInputModel item1 = new DocumentLineInputModel();
        item1.setDescripcion("item");
        item1.setCantidad(new BigDecimal("1"));
        item1.setPrecioUnitario(new BigDecimal("118"));

        detalle.add(item1);
        return detalle;
    }

}
