package org.openublpe.xmlbuilder.inputdata.generator.homologacion.grupo2;

import org.openublpe.xmlbuilder.inputdata.generator.GeneralData;
import org.openublpe.xmlbuilder.inputdata.generator.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.homologacion.HomologacionUtils;
import org.openublpe.xmlbuilder.core.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Caso16_InvoiceGenerator implements InvoiceInputGenerator {

    private static volatile InvoiceInputModel INVOICE;

    public static InvoiceInputModel getInstance() {
        InvoiceInputModel invoice = INVOICE;
        if (invoice == null) {
            synchronized (Caso16_InvoiceGenerator.class) {
                invoice = INVOICE;
                if (invoice == null) {

                    INVOICE = invoice = new InvoiceInputModel();
                    invoice.setSerie("FF12");
                    invoice.setNumero(5);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    invoice.setFechaEmision(calendar.getTimeInMillis());

                    invoice.setFirmante(GeneralData.getFirmante());
                    invoice.setProveedor(GeneralData.getProveedor());
                    invoice.setCliente(GeneralData.getClienteConRUC());

                    List<DetalleInputModel> detalle = new ArrayList<>();
                    invoice.setDetalle(detalle);

                    for (int i = 0; i < 6; i++) {
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

        return invoice;
    }

    @Override
    public InvoiceInputModel getInput() {
        return getInstance();
    }

}
