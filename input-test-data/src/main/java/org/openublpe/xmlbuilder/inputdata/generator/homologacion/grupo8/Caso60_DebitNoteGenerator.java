package org.openublpe.xmlbuilder.inputdata.generator.homologacion.grupo8;

import org.openublpe.xmlbuilder.core.models.catalogs.Catalog7;
import org.openublpe.xmlbuilder.core.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.core.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.inputdata.generator.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.inputdata.generator.homologacion.HomologacionUtils;

import java.util.Calendar;

public class Caso60_DebitNoteGenerator implements DebitNoteInputGenerator {

    private static volatile DebitNoteInputModel DEBIT_NOTE;

    public static DebitNoteInputModel getInstance() {
        DebitNoteInputModel debitNote = DEBIT_NOTE;
        if (debitNote == null) {
            synchronized (Caso60_DebitNoteGenerator.class) {
                debitNote = DEBIT_NOTE;
                if (debitNote == null) {

                    DEBIT_NOTE = debitNote = new DebitNoteInputModel();
                    debitNote.setSerie("FF14");
                    debitNote.setNumero(1);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    debitNote.setFechaEmision(calendar.getTimeInMillis());

                    // get invoice
                    InvoiceInputModel invoice = Caso53_InvoiceGenerator.getInstance();

                    // copy
                    debitNote.setFirmante(invoice.getFirmante());
                    debitNote.setProveedor(invoice.getProveedor());
                    debitNote.setCliente(invoice.getCliente());
                    debitNote.setDetalle(invoice.getDetalle());

                    // No se puede emitir una nota de debito sin tener al menos un detalle GRAVADO
                    DetalleInputModel item = new DetalleInputModel();
                    DEBIT_NOTE.getDetalle().add(item);
                    item.setDescripcion("Item");
                    item.setCantidad(HomologacionUtils.cantidadRandom());
                    item.setPrecioUnitario(HomologacionUtils.precioUnitarioRandom());
                    item.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

                    debitNote.setSerieNumeroInvoiceReference(invoice.getSerie() + "-" + invoice.getNumero());
                    debitNote.setDescripcionSustentoInvoiceReference("mi descripcion o sustento");
                }
            }
        }

        return debitNote;
    }

    @Override
    public DebitNoteInputModel getInput() {
        return getInstance();
    }

}
