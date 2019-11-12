package org.openublpe.xmlbuilder.data.impl;

import org.openublpe.xmlbuilder.data.AbstractInvoiceInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog7;

import java.math.BigDecimal;
import java.util.Optional;

public class Invoice_ComplexInputGenerator extends AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = getInvoiceTemplate();

        DetalleInputModel item1 = new DetalleInputModel();
        input.getDetalle().add(item1);
        item1.setDescripcion("item");
        item1.setCantidad(new BigDecimal("1"));
        item1.setPrecioUnitario(new BigDecimal("118"));
        item1.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

        DetalleInputModel item2 = new DetalleInputModel();
        input.getDetalle().add(item2);
        item2.setDescripcion("item");
        item2.setCantidad(new BigDecimal("1"));
        item2.setPrecioUnitario(new BigDecimal("100"));
        item2.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString());

        DetalleInputModel item3 = new DetalleInputModel();
        input.getDetalle().add(item3);
        item3.setDescripcion("item");
        item3.setCantidad(new BigDecimal("1"));
        item3.setPrecioUnitario(new BigDecimal("100"));
        item3.setTipoIGV(Catalog7.EXONERADO_OPERACION_ONEROSA.toString());

        DetalleInputModel item4 = new DetalleInputModel();
        input.getDetalle().add(item4);
        item4.setDescripcion("item");
        item4.setCantidad(new BigDecimal("1"));
        item4.setPrecioUnitario(new BigDecimal("100"));
        item4.setTipoIGV(Catalog7.INAFECTO_OPERACION_ONEROSA.toString());

        DetalleInputModel item5 = new DetalleInputModel();
        input.getDetalle().add(item5);
        item5.setDescripcion("item");
        item5.setCantidad(new BigDecimal("1"));
        item5.setPrecioUnitario(new BigDecimal("100"));
        item5.setTipoIGV(Catalog7.INAFECTO_RETIRO_POR_BONIFICACION.toString());


        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/invoice/Complex.xml");
    }

}
