package org.openublpe.xmlbuilder.data.impl;

import org.openublpe.xmlbuilder.data.AbstractInvoiceInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.catalogs.Catalog7;

import java.math.BigDecimal;
import java.util.Optional;

public class Invoice_GravadaNoOnerosaInputGenerator extends AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = getInvoiceTemplate();

        DetalleInputModel item1 = new DetalleInputModel();
        input.getDetalle().add(item1);

        item1.setDescripcion("item");
        item1.setCantidad(new BigDecimal("1"));
        item1.setPrecioUnitario(new BigDecimal("100"));
        item1.setTipoIGV(Catalog7.GRAVADO_RETIRO_POR_PREMIO.toString());

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/invoice/Gravada_NoOnerosa.xml");
    }

}
