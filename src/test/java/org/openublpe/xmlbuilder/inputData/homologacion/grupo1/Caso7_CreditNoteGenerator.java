package org.openublpe.xmlbuilder.inputData.homologacion.grupo1;

import org.openublpe.xmlbuilder.inputData.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.creditNote.CreditNoteInputModel;

import java.util.Date;

/**
 * Nota de credito de caso 3
 */
public class Caso7_CreditNoteGenerator implements CreditNoteInputGenerator {

    public static CreditNoteInputModel CREDIT_NOTE;

    @Override
    public CreditNoteInputModel getInput() {
        if (CREDIT_NOTE == null) {
            synchronized (this) {
                if (CREDIT_NOTE == null) {

                    CREDIT_NOTE = new CreditNoteInputModel();

                    CREDIT_NOTE.setSerie("FF11");
                    CREDIT_NOTE.setNumero(2);
                    CREDIT_NOTE.setFechaEmision(new Date().getTime());

                    // Get invoice
                    InvoiceInputModel invoice = Caso3_InvoiceGenerator.INVOICE;

                    // Copy
                    CREDIT_NOTE.setFirmante(invoice.getFirmante());
                    CREDIT_NOTE.setProveedor(invoice.getProveedor());
                    CREDIT_NOTE.setCliente(invoice.getCliente());
                    CREDIT_NOTE.setDetalle(invoice.getDetalle());

                    CREDIT_NOTE.setSerieNumeroInvoiceReference(invoice.getSerie() + "-" + invoice.getNumero());
                    CREDIT_NOTE.setDescripcionSustentoInvoiceReference("mi descripcion o sustento");
                }
            }
        }

        return CREDIT_NOTE;
    }
}
