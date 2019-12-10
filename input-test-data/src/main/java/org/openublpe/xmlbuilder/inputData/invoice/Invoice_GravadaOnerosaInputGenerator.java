package org.openublpe.xmlbuilder.inputData.invoice;

import org.openublpe.xmlbuilder.inputData.AbstractInvoiceInputGenerator;
import org.openublpe.xmlbuilder.inputData.InvoiceInputGenerator;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;

import java.math.BigDecimal;
import java.util.Optional;

public class Invoice_GravadaOnerosaInputGenerator extends AbstractInvoiceInputGenerator implements InvoiceInputGenerator {

    @Override
    public InvoiceInputModel getInput() {
        InvoiceInputModel input = getInvoiceTemplate();

        DetalleInputModel item1 = new DetalleInputModel();
        input.getDetalle().add(item1);

        item1.setDescripcion("item");
        item1.setCantidad(new BigDecimal("1"));
        item1.setPrecioUnitario(new BigDecimal("118"));
        item1.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/invoice/Gravada_Onerosa.xml");
    }

}
