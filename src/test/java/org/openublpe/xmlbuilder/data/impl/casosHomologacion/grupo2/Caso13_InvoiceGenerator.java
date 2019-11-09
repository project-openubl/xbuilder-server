package org.openublpe.xmlbuilder.data.impl.casosHomologacion.grupo2;

import org.openublpe.xmlbuilder.data.ClienteInputGenerator;
import org.openublpe.xmlbuilder.data.FirmanteInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.data.ProveedorInputGenerator;
import org.openublpe.xmlbuilder.data.impl.casosHomologacion.HomologacionUtils;
import org.openublpe.xmlbuilder.models.input.general.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Factura con 4 items
 */
public class Caso13_InvoiceGenerator implements InvoiceInputGenerator {

    public static InvoiceInputModel INVOICE;

    @Override
    public InvoiceInputModel getInvoice() {
        if (INVOICE == null) {
            synchronized (this) {
                if (INVOICE == null) {

                    INVOICE = new InvoiceInputModel();
                    INVOICE.setSerie("FF12");
                    INVOICE.setNumero(2);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    INVOICE.setFechaEmision(calendar.getTimeInMillis());

                    INVOICE.setFirmante(FirmanteInputGenerator.getFirmante());
                    INVOICE.setProveedor(ProveedorInputGenerator.getProveedor());
                    INVOICE.setCliente(ClienteInputGenerator.getClienteConRUC());

                    List<DetalleInputModel> detalle = new ArrayList<>();
                    INVOICE.setDetalle(detalle);

                    for (int i = 0; i < 4; i++) {
                        DetalleInputModel item = new DetalleInputModel();
                        detalle.add(item);
                        item.setDescripcion("Item" + (i + 1));
                        item.setCantidad(HomologacionUtils.cantidadRandom());
                        item.setPrecioUnitario(HomologacionUtils.precioUnitarioRandom());
                        item.setTipoIGV(HomologacionUtils.tipoIGVInafectaExoneradaRandom());
                    }
                }
            }
        }

        return INVOICE;
    }

}
