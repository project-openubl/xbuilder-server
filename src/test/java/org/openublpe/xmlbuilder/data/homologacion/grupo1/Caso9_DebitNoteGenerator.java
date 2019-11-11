package org.openublpe.xmlbuilder.data.homologacion.grupo1;

import org.openublpe.xmlbuilder.data.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.data.GeneralData;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.general.note.debitNote.DebitNoteInputModel;

import java.util.Date;

/**
 * Nota de debito de caso 2
 */
public class Caso9_DebitNoteGenerator implements DebitNoteInputGenerator {

    public static DebitNoteInputModel DEBIT_NOTE;

    @Override
    public DebitNoteInputModel getInput() {
        if (DEBIT_NOTE == null) {
            synchronized (this) {
                if (DEBIT_NOTE == null) {

                    DEBIT_NOTE = new DebitNoteInputModel();

                    DEBIT_NOTE.setSerie("FF11");
                    DEBIT_NOTE.setNumero(1);
                    DEBIT_NOTE.setFechaEmision(new Date().getTime());

                    DEBIT_NOTE.setFirmante(GeneralData.getFirmante());

                    // Get invoice
                    InvoiceInputModel invoice = Caso2_InvoiceGenerator.INVOICE;

                    // Copy
                    DEBIT_NOTE.setProveedor(invoice.getProveedor());
                    DEBIT_NOTE.setCliente(invoice.getCliente());
                    DEBIT_NOTE.setDetalle(invoice.getDetalle());

                    DEBIT_NOTE.setSerieNumeroInvoiceReference(invoice.getSerie() + "-" + invoice.getNumero());
                    DEBIT_NOTE.setDescripcionSustentoInvoiceReference("mi descripcion o sustento");
                }
            }
        }

        return DEBIT_NOTE;
    }

}
