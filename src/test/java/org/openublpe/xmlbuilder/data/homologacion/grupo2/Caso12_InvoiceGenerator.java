package org.openublpe.xmlbuilder.data.homologacion.grupo2;

import org.openublpe.xmlbuilder.data.GeneralData;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog7;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Factura con 1 items
 */
public class Caso12_InvoiceGenerator implements InvoiceInputGenerator {

    public static InvoiceInputModel INVOICE;

    @Override
    public InvoiceInputModel getInput() {
        if (INVOICE == null) {
            synchronized (this) {
                if (INVOICE == null) {

                    INVOICE = new InvoiceInputModel();
                    INVOICE.setSerie("FF12");
                    INVOICE.setNumero(1);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    INVOICE.setFechaEmision(calendar.getTimeInMillis());

                    INVOICE.setFirmante(GeneralData.getFirmante());
                    INVOICE.setProveedor(GeneralData.getProveedor());
                    INVOICE.setCliente(GeneralData.getClienteConRUC());

                    List<DetalleInputModel> detalle = new ArrayList<>();
                    INVOICE.setDetalle(detalle);

                    for (int i = 0; i < 1; i++) {
                        DetalleInputModel item = new DetalleInputModel();
                        detalle.add(item);
                        item.setDescripcion("Item" + (i + 1));
//                        item.setCantidad(HomologacionUtils.cantidadRandom());
//                        item.setPrecioUnitario(HomologacionUtils.precioUnitarioRandom());
//                        item.setTipoIGV(HomologacionUtils.tipoIGVInafectaExoneradaRandom());
                        item.setCantidad(new BigDecimal("1"));
                        item.setPrecioUnitario(new BigDecimal("30"));
                        item.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_ENTREGA_A_TRABAJADORES.toString());
                    }
                }
            }
        }

        return INVOICE;
    }

}
