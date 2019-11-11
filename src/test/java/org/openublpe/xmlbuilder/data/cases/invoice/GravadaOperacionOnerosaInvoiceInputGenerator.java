package org.openublpe.xmlbuilder.data.cases.invoice;

import org.openublpe.xmlbuilder.data.AbstractInvoiceInputGenerator;
import org.openublpe.xmlbuilder.data.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.models.input.general.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.ubl.Catalog7;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;

public class GravadaOperacionOnerosaInvoiceInputGenerator extends AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = getInvoiceTemplate();

        DetalleInputModel item1 = new DetalleInputModel();
        item1.setDescripcion("item1");
        item1.setCantidad(new BigDecimal("10"));
        item1.setPrecioUnitario(new BigDecimal("20"));
        item1.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

        DetalleInputModel item2 = new DetalleInputModel();
        item2.setDescripcion("item2");
        item2.setCantidad(new BigDecimal("10"));
        item2.setPrecioUnitario(new BigDecimal("20"));
        item2.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

        input.getDetalle().add(item1);
        input.getDetalle().add(item2);

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/invoice/Gravada_OperacionOnerosa.xml");
    }

}
