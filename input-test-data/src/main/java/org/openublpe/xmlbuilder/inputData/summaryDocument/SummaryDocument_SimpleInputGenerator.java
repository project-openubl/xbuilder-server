package org.openublpe.xmlbuilder.inputData.summaryDocument;

import org.openublpe.xmlbuilder.inputData.GeneralData;
import org.openublpe.xmlbuilder.inputData.SummaryDocumentInputGenerator;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.catalogs.Catalog19;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentInputModel;
import org.openublpe.xmlbuilder.core.models.input.sunat.SummaryDocumentLineInputModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class SummaryDocument_SimpleInputGenerator implements SummaryDocumentInputGenerator {

    @Override
    public SummaryDocumentInputModel getInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        SummaryDocumentInputModel input = new SummaryDocumentInputModel();
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        input.setFechaEmisionDocumentReference(calendar.getTimeInMillis());

        input.setFirmante(GeneralData.getFirmante());
        input.setProveedor(GeneralData.getProveedor());

        List<SummaryDocumentLineInputModel> detalle = new ArrayList<>();
        input.setDetalle(detalle);

        SummaryDocumentLineInputModel item = new SummaryDocumentLineInputModel();
        detalle.add(item);


        item.setTipoComprobante(Catalog1.BOLETA.toString());
        item.setSerieNumero("B001-1");
        item.setCliente(GeneralData.getClienteConDNI());
        item.setTipoOperacion(Catalog19.ADICIONAR.toString());
        item.setImporteTotal(new BigDecimal("100"));
        item.setTotalOperacionesGravadas(new BigDecimal("100"));
        item.setIgv(new BigDecimal("18"));

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
//        return Optional.of("xml/voidedDocument/Simple.xml");
        return Optional.empty();
    }
}
