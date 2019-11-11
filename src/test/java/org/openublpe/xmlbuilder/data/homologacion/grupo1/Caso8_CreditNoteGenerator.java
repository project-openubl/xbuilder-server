package org.openublpe.xmlbuilder.data.homologacion.grupo1;

import org.openublpe.xmlbuilder.data.CreditNoteInputGenerator;
import org.openublpe.xmlbuilder.data.GeneralData;
import org.openublpe.xmlbuilder.models.input.general.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.general.note.creditNote.CreditNoteInputModel;

import java.util.Date;

/**
 * Nota de credito de caso 4
 */
public class Caso8_CreditNoteGenerator implements CreditNoteInputGenerator {

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

                    CREDIT_NOTE.setFirmante(GeneralData.getFirmante());

                    // Get invoice
                    InvoiceInputModel invoice = Caso4_InvoiceGenerator.INVOICE;

                    // Copy
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
