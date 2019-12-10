package org.openublpe.xmlbuilder.inputData.homologacion.grupo4;

import org.openublpe.xmlbuilder.inputData.GeneralData;
import org.openublpe.xmlbuilder.inputData.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputData.homologacion.HomologacionUtils;
import org.openublpe.xmlbuilder.core.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Caso33_InvoiceGenerator implements InvoiceInputGenerator {

    private static volatile InvoiceInputModel INVOICE;

    public static InvoiceInputModel getInstance() {
        InvoiceInputModel invoice = INVOICE;
        if (invoice == null) {
            synchronized (Caso33_InvoiceGenerator.class) {
                invoice = INVOICE;
                if (invoice == null) {

                    INVOICE = invoice = new InvoiceInputModel();
                    invoice.setSerie("FF14");
                    invoice.setNumero(2);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    invoice.setFechaEmision(calendar.getTimeInMillis());

                    invoice.setFirmante(GeneralData.getFirmante());
                    invoice.setProveedor(GeneralData.getProveedor());
                    invoice.setCliente(GeneralData.getClienteConRUC());

                    invoice.setTotalDescuentos(BigDecimal.ONE);

                    List<DetalleInputModel> detalle = new ArrayList<>();
                    invoice.setDetalle(detalle);

                    for (int i = 0; i < 1; i++) {
                        DetalleInputModel item = new DetalleInputModel();
                        detalle.add(item);
                        item.setDescripcion("Item" + (i + 1));
                        item.setCantidad(HomologacionUtils.cantidadRandom());
                        item.setPrecioUnitario(HomologacionUtils.precioUnitarioRandom());
                    }
                }
            }
        }

        return invoice;
    }

    @Override
    public InvoiceInputModel getInput() {
        return getInstance();
    }
}
