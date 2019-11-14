package org.openublpe.xmlbuilder.data.homologacion.grupo2;

import org.openublpe.xmlbuilder.data.DebitNoteInputGenerator;
import org.openublpe.xmlbuilder.data.homologacion.HomologacionUtils;
import org.openublpe.xmlbuilder.models.input.standard.DetalleInputModel;
import org.openublpe.xmlbuilder.models.input.standard.invoice.InvoiceInputModel;
import org.openublpe.xmlbuilder.models.input.standard.note.debitNote.DebitNoteInputModel;
import org.openublpe.xmlbuilder.models.catalogs.Catalog7;

import java.util.Calendar;

public class Caso21_DebitNoteGenerator implements DebitNoteInputGenerator {

    public static DebitNoteInputModel DEBIT_NOTE;

    @Override
    public DebitNoteInputModel getInput() {
        if (DEBIT_NOTE == null) {
            synchronized (this) {
                if (DEBIT_NOTE == null) {

                    DEBIT_NOTE = new DebitNoteInputModel();
                    DEBIT_NOTE.setSerie("FF12");
                    DEBIT_NOTE.setNumero(2);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(2019, Calendar.NOVEMBER, 9, 8, 30, 0);
                    DEBIT_NOTE.setFechaEmision(calendar.getTimeInMillis());

                    // get invoice
                    InvoiceInputModel invoice = Caso14_InvoiceGenerator.INVOICE;

                    // copy
                    DEBIT_NOTE.setFirmante(invoice.getFirmante());
                    DEBIT_NOTE.setProveedor(invoice.getProveedor());
                    DEBIT_NOTE.setCliente(invoice.getCliente());
                    DEBIT_NOTE.setDetalle(invoice.getDetalle());

                    // No se puede emitir una nota de debito sin tener al menos un detalle GRAVADO
                    DetalleInputModel item = new DetalleInputModel();
                    DEBIT_NOTE.getDetalle().add(item);
                    item.setDescripcion("Item");
                    item.setCantidad(HomologacionUtils.cantidadRandom());
                    item.setPrecioUnitario(HomologacionUtils.precioUnitarioRandom());
                    item.setTipoIGV(Catalog7.GRAVADO_OPERACION_ONEROSA.toString());

                    DEBIT_NOTE.setSerieNumeroInvoiceReference(invoice.getSerie() + "-" + invoice.getNumero());
                    DEBIT_NOTE.setDescripcionSustentoInvoiceReference("mi descripcion o sustento");
                }
            }
        }

        return DEBIT_NOTE;
    }

}
