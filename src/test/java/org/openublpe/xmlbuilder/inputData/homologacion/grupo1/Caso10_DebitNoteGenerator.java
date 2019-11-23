package org.openublpe.xmlbuilder.inputData.homologacion.grupo1;

import org.openublpe.xmlbuilder.inputData.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.debitNote.DebitNoteInputModel;

import java.util.Date;

/**
 * Nota de debito de caso 3
 */
public class Caso10_DebitNoteGenerator implements DebitNoteInputGenerator {

    private static volatile DebitNoteInputModel DEBIT_NOTE;

    public static DebitNoteInputModel getInstance() {
        DebitNoteInputModel debitNote = DEBIT_NOTE;
        if (debitNote == null) {
            synchronized (Caso10_DebitNoteGenerator.class) {
                debitNote = DEBIT_NOTE;
                if (debitNote == null) {

                    DEBIT_NOTE = debitNote = new DebitNoteInputModel();

                    debitNote.setSerie("FF11");
                    debitNote.setNumero(2);
                    debitNote.setFechaEmision(new Date().getTime());

                    // Get invoice
                    InvoiceInputModel invoice = Caso3_InvoiceGenerator.getInstance();

                    // Copy
                    debitNote.setFirmante(invoice.getFirmante());
                    debitNote.setProveedor(invoice.getProveedor());
                    debitNote.setCliente(invoice.getCliente());
                    debitNote.setDetalle(invoice.getDetalle());

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
