package org.openublpe.xmlbuilder.inputdata.generator.voidedDocument;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog1;
import org.openublpe.xmlbuilder.core.models.input.sunat.VoidedDocumentInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.GeneralData;
import org.openublpe.xmlbuilder.inputdata.generator.VoidedDocumentInputGenerator;

import java.util.Calendar;
import java.util.Optional;

public class VoidedDocument_SimpleInputGenerator implements VoidedDocumentInputGenerator {

    @Override
    public VoidedDocumentInputModel getInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.NOVEMBER, 8);


        VoidedDocumentInputModel input = new VoidedDocumentInputModel();
        input.setNumero(1);
        input.setFechaEmision(calendar.getTimeInMillis());

        input.setFirmante(GeneralData.getFirmante());
        input.setProveedor(GeneralData.getProveedor());

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        input.setSerieNumeroDocumentReference("F001-1");
        input.setFechaEmisionDocumentReference(calendar.getTimeInMillis());
        input.setMotivoBajaDocumentReference("motivo baja");
        input.setTipoDocumentReference(Catalog1.FACTURA.toString());

        return input;
    }

    @Override
    public Optional<String> getSnapshot() {
        return Optional.of("xml/voidedDocument/Simple.xml");
    }
}
