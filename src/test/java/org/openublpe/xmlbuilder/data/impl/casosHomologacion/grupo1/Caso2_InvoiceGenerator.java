package org.openublpe.xmlbuilder.data.impl.casosHomologacion.grupo1;

import org.openublpe.xmlbuilder.data.ClienteInputGenerator;
import org.openublpe.xmlbuilder.data.FirmanteInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.data.ProveedorInputGenerator;
import org.openublpe.xmlbuilder.data.impl.casosHomologacion.HomologacionUtils;
import org.openublpe.xmlbuilder.models.input.general.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Factura con 2 items
 */
public class Caso2_InvoiceGenerator implements InvoiceInputGenerator {

    public static InvoiceInputModel INVOICE;

    @Override
    public InvoiceInputModel getInvoice() {
        if (INVOICE == null) {
            synchronized (this) {
                if (INVOICE == null) {

                    INVOICE = new InvoiceInputModel();
                    INVOICE.setSerie("FF11");
                    INVOICE.setNumero(2);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    INVOICE.setFechaEmision(calendar.getTimeInMillis());

                    INVOICE.setFirmante(FirmanteInputGenerator.getFirmante());
                    INVOICE.setProveedor(ProveedorInputGenerator.getProveedor());
                    INVOICE.setCliente(ClienteInputGenerator.getClienteConRUC());

                    List<DetalleInputModel> detalle = new ArrayList<>();
                    INVOICE.setDetalle(detalle);

                    for (int i = 0; i < 2; i++) {
                        DetalleInputModel item = new DetalleInputModel();
                        detalle.add(item);
                        item.setDescripcion("Item" + (i + 1));
                        item.setCantidad(HomologacionUtils.cantidadRandom());
                        item.setPrecioUnitario(HomologacionUtils.precioUnitarioRandom());
                    }
                }
            }
        }

        return INVOICE;
    }

}
